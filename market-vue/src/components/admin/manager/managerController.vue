<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>管理员管理</h2>
      </div>
      <div>
        <el-button v-if="!currentAdmin.roleLevel"  type="primary" icon="el-icon-circle-plus" @click="addMessage">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="deleteMessage">删除</el-button>
        <el-button type="success" icon="el-icon-edit" @click="editMessage">编辑</el-button>
      </div>
    </el-card>
    <br/>

    <el-table :data="this.adminListShow" :border="true" ref="messageTable" height="500">
      <el-table-column type="selection"></el-table-column>
<!--            <el-table-column prop="image" label="商品">-->
<!--              <template slot-scope="scope">-->
<!--                <img :src="scope.row.image" alt="类别" style="height: 64px; width: 64px" />-->
<!--              </template>-->
<!--            </el-table-column>-->
      <el-table-column prop="adminAccount" label="管理员账号"></el-table-column>
      <el-table-column prop="adminUsername" label="管理员姓名"></el-table-column>
      <el-table-column prop="sex" label="性别">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 0 ? 'danger' : 'primary'"
              disable-transitions>{{scope.row.sex === 0 ? '女' : '男'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="管理员手机号"></el-table-column>
      <el-table-column prop="roleLevel" label="管理员等级">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleLevel !== 0 ? 'info' : 'success'"
              disable-transitions>{{scope.row.roleLevel !== 0 ? '普通管理员' : '超级管理员'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isLocked" label="状态">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isLocked === 0 ? 'primary' : 'danger'"
              disable-transitions>{{scope.row.isLocked === 0 ? '正常' : '封锁'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间">
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
        :total="adminList.length"
        layout="sizes, prev, pager, next, jumper"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"
    ></el-pagination>


    <el-dialog :visible="dialogVisible" title="新增/编辑管理员" @close="closeDialog">
      <el-form ref="editForm" :model="editForm" label-width="100px">
        <el-form-item label="管理员账号">
          <el-input v-model="editForm.adminAccount" name="managerAccount"></el-input>
        </el-form-item>
        <el-form-item label="管理员姓名">
          <el-input v-model="editForm.adminUsername" name="managerName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <template>
            <el-radio-group v-model="editForm.sex" name="gender" class="el-radio-box">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </template>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" name="level"></el-input>
        </el-form-item>
        <el-form-item label="管理员等级">
          <template>
            <el-radio-group v-model="editForm.roleLevel" name="level" class="el-radio-box">
              <el-radio :label="1">普通管理员</el-radio>
            </el-radio-group>
          </template>
        </el-form-item>
        <el-form-item label="状态">
          <template>
          <el-radio-group v-model="editForm.isLocked" name="gender" class="el-radio-box">
            <el-radio :label="1">封锁</el-radio>
            <el-radio :label="0">正常</el-radio>
          </el-radio-group>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveMessage">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name:"managerController",
  data() {
    return {
      //当前管理员的信息：
      currentAdmin:localStorage.getItem('admin'),
      recentManger:{
      },
      adminList: [],
      dialogVisible: false, // 编辑轮播图对话框可见性
      editForm: {
        adminAccount:'',
        adminUsername: '',
        sex: '',
        roleLevel: '',
        password: '',
        isLocked:'',
        avatarUrl:'',
        createTime:'',

      },
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      isAddingCarousel: false,

      //删除列表
      deleteList:[],
    };
  },
  beforeMount(){
    this.getAdminData();
  },
  computed: {

    adminListShow() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.adminList.slice(start, end);
    },
  },
  methods: {
    getAdminData(){
      let _this = this
      _this.$axios({
        url:this.$http + '/admin/list',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {
          _this.adminList= res.data;
        }
        else{
          alert("失败")
        }
      });

    },
    addMessage() {
      // 新增轮播图
      this.dialogVisible = true; // 显示对话框
      this.resetForm(); // 重置表单数据
      this.isAddingCarousel = true; // 设置标志位为true
    },
    deleteMessage() {
      // 删除轮播图
      const selectedRows = this.$refs.messageTable.selection; // 获取选中的行数据

      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }
      this.$confirm('此操作将删除该管理员信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //这段连接后端之后其实就没用了 但是可以显示的更快
        this.adminList = this.adminList.filter(admin => !selectedRows.includes(admin));

        for (let i = 0; i < selectedRows.length; i++) {
          this.deleteList.push(selectedRows[i].adminId);
        }
        //后端
        this.$axios({
          url:this.$http + '/admin/delete?adminId=' + this.deleteList,
          method:'get',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){
            // console.log("3339")
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getGoodData()
          }else{
            this.$message.error("删除失败")
          }
        })


      }).catch(()=>{
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })

    },
    editMessage() {
      // 获取选中的行数据
      const selectedRows = this.$refs.messageTable.selection;

      if (selectedRows.length === 0) {
        this.$message.warning('请选择要编辑的数据');
        return;
      }else if(selectedRows.length !== 1){
        this.$message.warning("抱歉,您只能选择一条数据")
        return;
      }
      // 将当前轮播图的信息赋值给表单数据
      this.editForm = { ...selectedRows[0] };
      // 编辑轮播图
      this.dialogVisible = true;

      // 设置标志位为false
      this.isAddingCarousel = false;
    },
    resetForm() {
      // 重置表单
      this.editForm = {
        adminId:0,
        adminUsername: '',
        sex: '',
        roleLevel: '',
        password: '',
        isLocked:'',
        avatarUrl:'',
        createTime:'',

      };
    },
    closeDialog(){
      this.dialogVisible = false
      this.resetForm()
    },
    saveMessage() {
      let newMessage = null;
      if (this.isAddingCarousel) {
        const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
        const maxId = Math.max(...this.adminList.map(admin => admin.adminId));
        let newId = 1;
        if(maxId!==-Infinity){
          newId = maxId + 1
        }
        newMessage = {
          adminId: newId,
          //测试数据image
          //image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'),
          adminAccount: this.editForm.adminAccount,
          adminUsername: this.editForm. adminUsername,
          gender: this.editForm.sex,
          roleLevel: this.editForm.roleLevel,
          password:this.editForm.password,
          isLocked:this.editForm.isLocked,
          avatarUrl:this.editForm.avatarUrl,
          createTime:formattedDate,
        };
        // console.log(newMessage)
      }

      //标记是否有新增错误
      let flag = false
      if(this.editForm.adminAccount ==="" || this.editForm.adminUsername ==="" ||this.editForm.sex ==="" ||this.editForm.roleLevel ===null ||
          this.editForm.password ==="" ||this.editForm.isLocked ===""){
        this.$message.error("请输入完整信息")
        return;
      }else {
        flag = true;
      }

      if (this.isAddingCarousel) {
        this.adminList.push(newMessage);
      }
      const index = this.adminList.findIndex(adminData => adminData.adminId === this.editForm.adminId);
      if (index !== -1) {
        this.adminList.splice(index, 1, { ...this.editForm });
      }

      //判断是否为空的拦截
      if (this.isAddingCarousel && flag) {
        const index = this.adminList.length - 1
        this.save(index)
        this.$message.success('新增管理员成功');
      } else {
        this.save(index)
        this.$message.success('编辑管理员信息成功');
      }

      this.dialogVisible = false; // 关闭编辑对话框
    },
    save(index){
      let data = this.adminList[index];
      this.$axios({
        url:this.$http + '/admin/saveOrModGood',
        method:'post',
        data:data,
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
          this.getAdminData();
        }else{
          this.getAdminData();
          alert('更新数据失败')
        }
      })
    },
    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    },

    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize;
      // 当项数改变时，重置当前页为第一页
      this.currentPage = 1;
    },
  }
};
</script>

<style scoped>
.el-radio-box{
  margin-left: 2vw;
}
</style>

