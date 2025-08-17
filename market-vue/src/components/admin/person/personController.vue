<template>
  <div>
    <el-card>
    <div>
      <div slot="header">
        <h2>用户管理</h2>
      </div>
    <el-divider></el-divider>
      <div class="search-container">
        <el-button type="primary" style="margin-left: 5px;" icon="el-icon-circle-plus" @click="add">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="deleteRows">删除</el-button>
        <el-button type="success" icon="el-icon-edit" @click="edit()">编辑</el-button>
        <el-input
            v-model="searchKeyword"
            placeholder="请输入用户名关键字"
            @input="search"
            style="margin-left: 5vh;"
        ></el-input>
      </div>
      <br/>

<!--      新增对话框-->
      <el-dialog
          title="新增用户"
          :visible.sync="showDialog"
          width="30%"
          @keydown.enter.native="handleConfirm"
      >
        <!-- 在弹框中添加表单元素，用于输入属性数据 -->
        <el-form ref="tableData" :model="editItem">
          <el-form-item label="账号">
            <el-input v-model="editItem.account" @blur="validateAccount"></el-input>
            <span v-if="accountError" class="error-message">{{ accountError }}</span>
<!--            <span v-if="!accountPattern.test(editItem.account)" class="error-message">账号限制为纯数字组成，长度为6到12位</span>-->
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="editItem.userName"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="editItem.password" @blur="validatePassword"></el-input>
            <span v-if="passwordError" class="error-message">{{ passwordError }}</span>
<!--            <span v-if="!passwordPattern.test(editItem.password)" class="error-message">密码限制为包含字母和数字，长度为6到16位</span>-->
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="editItem.sex" placeholder="请选择">
              <el-option
                  v-for="s in optionsSex"
                  :key="s.value"
                  :label="s.label"
                  :value="s.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所在校园">
            <!-- 学校选择器 -->
            <el-select v-model="editItem.campusName" placeholder="请选择学校">
              <!-- 学校选项 -->
              <el-option
                  v-for="campus in campuses"
                  :key="campus.campusId"
                  :label="campus.campusName"
                  :value="campus.campusName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否封锁">
            <el-select v-model="editItem.isLocked" placeholder="请选择">
              <el-option
                  v-for="ex in options"
                  :key="ex.value"
                  :label="ex.label"
                  :value="ex.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- 其他表单项... -->
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button ref="confirmButton" type="primary" :disabled="disableConfirmButton" @click="saveProperty">确认</el-button>
        </span>
      </el-dialog>

      <!--      编辑对话框-->
      <el-dialog
          title="编辑用户"
          :visible.sync="showEditDialog"
          width="30%"
          @keydown.enter.native="handleConfirm"
      >
        <el-form ref="editForm" :model="editItem">
          <el-form-item label="账号">
            <el-input v-model="editItem.account" @blur="validateAccount"></el-input>
            <span v-if="accountError" class="error-message">{{ accountError }}</span>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="editItem.userName"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="editItem.sex" placeholder="请选择">
              <el-option
                  v-for="s in optionsSex"
                  :key="s.value"
                  :label="s.label"
                  :value="s.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所在校园">
            <!-- 学校选择器 -->
            <el-select v-model="editItem.campusName" placeholder="请选择学校">
              <!-- 学校选项 -->
              <el-option
                  v-for="campus in campuses"
                  :key="campus.campusId"
                  :label="campus.campusName"
                  :value="campus.campusName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否封锁">
            <el-select v-model="editItem.isLocked" placeholder="请选择">
              <el-option
                  v-for="ex in options"
                  :key="ex.value"
                  :label="ex.label"
                  :value="ex.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- 其他表单项... -->
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button ref="confirmButton" type="primary" @click="saveEdit">确认</el-button>
        </span>
      </el-dialog>

    </div>
    </el-card>

    <div>
      <el-main>
        <el-table
            :data="pagedCarouselList"
            height="500"
            :border="true"
            ref="personTable">
          <el-table-column type="selection" v-model="selectedRows"></el-table-column>
          <el-table-column prop="account" label="账号">
          </el-table-column>
          <el-table-column prop="userName" label="用户名">
          </el-table-column>
          <el-table-column prop="password" label="密码">
          </el-table-column>
          <el-table-column prop="sex" label="性别">
            <template slot-scope="scope">
              <el-tag
                  :type="scope.row.sex === 1 ? 'success' : 'danger'"
                  disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="campusName" label="所在校园">
          </el-table-column>
          <el-table-column prop="registerDate" label="注册日期">
          </el-table-column>
          <el-table-column prop="isLock" label="是否被锁">
            <template slot-scope="scope">
              <el-tag
                  :type="scope.row.isLocked === 0 ? 'success' : 'danger'"
                  disable-transitions>{{scope.row.isLocked === 0 ? '正常' : '锁定'}}</el-tag>
            </template>
          </el-table-column>
        </el-table>

        <!--      page-sizes 可选择的每页项数 -->
        <!--       layout 布局包括项数选择、上一页、页码、下一页、跳转 -->
        <!--      @size-change 项数选择改变时的事件-->
        <!--      current-change 页码改变时的事件-->
        <el-pagination
            :current-page="currentPage"
            :page-sizes="[2, 5, 10]"
            :page-size="pageSize"
            :total="displayData.length"
            layout="sizes, prev, pager, next, jumper"
            @size-change="handlePageSizeChange"
            @current-change="handlePageChange"
        ></el-pagination>

      </el-main>
    </div>
  </div>
</template>

<script>
export default {
  name: "personController",
  data() {
    return {
      options: [{
        value: 1,
        label: '锁定'
      }, {
        value: 0,
        label: '正常'
      }],//是否被锁选择框
      optionsSex:[{
        value: 0,
        label: '女'
      }, {
        value: 1,
        label: '男'
      }],//性别选择框
      tableData: [],//初始数据
      total:0,//数据条数
      showDialog:false,//新增记录对话框
      searchDialog:false,//查询记录对话框
      showEditDialog:false,//编辑记录对话框
      searchKeyword: '', // 搜索关键字
      filteredData: [], // 过滤后的数据
      selectedRows:[],//选中的数据
      editItem: {
        account:'',
        userName:'',
        password: 123,
        sex:0,
        campusName:'',
        isLocked:0,
      }, // 存储当前编辑项的数据
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      accountError: '', // 账户输入错误提示
      passwordError: '', // 密码输入错误提示
      disableConfirmButton: false, // 是否禁用确认按钮

      campuses: [], // 学校列表

      //删除列表
      deleteList:[],
    }
  },
  methods:{
    search(){
      // 构建正则表达式，匹配包含关键字的项
      const regex = new RegExp(this.searchKeyword, 'i');

      // 执行模糊查询
      this.filteredData = this.tableData.filter((item) =>
          regex.test(item.userName)
      );
      // // console.log(this.filteredData);
      // 检查过滤后的数据长度
      if (this.filteredData.length === 0) {
        // 弹出提示框，提示未找到指定数据
        this.$message({
          type: 'warning',
          message: '未找到指定数据'
        });
      }
    },
    add(){
      this.resetForm();
      this.showDialog = true;
    },
    deleteRows(){
      // 获取选中的行数据
      const selectedRows = this.$refs.personTable.selection;
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }
      for (let i = 0; i < selectedRows.length; i++) {
        this.deleteList.push(selectedRows[i].personId);
      }
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tableData = this.tableData.filter(person => !selectedRows.includes(person));
        this.filteredData = this.filteredData.filter(person => !selectedRows.includes(person));
        //调用后端
        this.$axios({
          url:this.$http + '/person/delete?personId=' + this.deleteList,
          method:'get',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){
            this.$message({
              message: '删除成功！',
              type: 'success'
            });
            this.loadPerson();
          }else{
            this.$message({
              message: '删除失败！',
              type: 'error'
            });
          }

        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    saveProperty() {
      // 构建要新增的记录对象
      // 获取当前tableData中的最大id值，并在其基础上加1，作为新增用户的id
      const maxId = Math.max(...this.tableData.map(user => user.personId));
      let newId = 1;
      if(maxId!==-Infinity){
        newId = maxId + 1
      }
      if(this.editItem.account===''||this.editItem.userName===''||this.editItem.campusName===''){
        this.$message.warning('请填写完整的数据');
        return;
      }
      const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
      const newItem = {
        personId: newId,
        account: this.editItem.account,
        userName: this.editItem.userName,
        password: this.editItem.password,
        sex:this.editItem.sex,
        campusName: this.editItem.campusName,
        registerDate: formattedDate,
        isLocked: this.editItem.isLocked,
      };

      // 将新记录添加到表格数据数组中
      this.tableData.push(newItem);

      //后端操作
      this.$axios({
        url:this.$http + '/person/add',
        method:'post',
        data:newItem,
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){

          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.loadPerson()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })

      this.resetForm();
      // 保存完成后，关闭弹框
      this.showDialog = false;
    },
    handleConfirm() {
      // 触发确认按钮的点击事件
      this.$refs.confirmButton.$el.click();
    },
    edit() {
      // 获取选中的行数据
      const selectedRows = this.$refs.personTable.selection;

      if (selectedRows.length === 0) {
        this.$message.warning('请选择要编辑的数据');
        return;
      }else if(selectedRows.length !== 1){
        this.$message.warning("抱歉,您只能选择一条数据")
        return;
      }
      // 将当前用户的信息赋值给表单数据
      this.editItem = { ...selectedRows[0] };
      // 编辑用户
      this.showEditDialog = true; // 打开编辑对话框
    },
    saveEdit() {
      // 找到编辑项在原始数据中的索引
      const index = this.tableData.findIndex((item) => item.personId === this.editItem.personId);
      if (index !== -1) {
        // 更新原始数据中对应的项
        this.tableData.splice(index, 1, Object.assign({}, this.editItem));
        //后端操作
        this.$axios({
          url:this.$http + '/person/update',
          method:'post',
          data:this.editItem,
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){

            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadPerson()
          }else{
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }

        })

      }
      this.resetForm();
      this.showEditDialog = false; // 关闭编辑对话框
    },
    resetForm() {
      // 重置表单
      this.editItem = {
        account:'',
        userName:'',
        campusName:'',
        isLocked:0,
      };
    },
    cancelForm(){
      this.resetForm();
      this.showDialog = false;
      this.showEditDialog = false;
    },
    // 在页码变化时更新当前页数，并触发计算属性pagedCarouselList的重新计算
    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    },
    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize;
      // 当项数改变时，重置当前页为第一页
      this.currentPage = 1;
    },
    loadPerson(){
      this.$axios({
        url:this.$http + '/person/listP',
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
          this.tableData=res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
    validateAccount() {
      const { account } = this.editItem;
      if (!/^[0-9]{6,12}$/.test(account)) {
        this.accountError = '账号限制为纯数字组成，长度为6到12位';
        this.disableConfirmButton = true;
      } else {
        this.accountError = '';
        this.disableConfirmButton = false;
      }
    },
    validatePassword() {
      const { password } = this.editItem;
      if (!/^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$/.test(password)) {
        this.passwordError = '密码要求字母和数字结合且长度大于6位';
        this.disableConfirmButton = true;
      } else {
        this.passwordError = '';
        this.disableConfirmButton = false;
      }
    },
    // 获取学校列表
    fetchCampuses() {
      this.$axios({
        url:this.$http + '/near-sell/listCampuses',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.campuses = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
  },
  computed:{
    // 根据是否有过滤数据，动态决定显示哪个数据源
    displayData() {
      return this.filteredData.length ? this.filteredData : this.tableData;
    },
    pagedCarouselList() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.displayData.slice(start, end);
    },


  },
  beforeMount() {
    this.loadPerson();
    this.fetchCampuses()
  },
}
</script>

<style scoped>
.search-container {
  display: flex;
  align-items: center;
  height: 40px;
}
.error-message {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}

</style>