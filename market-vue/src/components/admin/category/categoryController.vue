<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>分类管理</h2>
      </div>
      <div class="search-container">
        <el-button type="primary" icon="el-icon-circle-plus" @click="addMessage">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="deleteMessage">删除</el-button>
        <el-button type="success" icon="el-icon-edit" @click="editMessage">编辑</el-button>
        <el-input
            v-model="searchKeyword"
            placeholder="请输入类别名关键字"
            icon="el-icon-search"
            @input="search"
            style="margin-left: 5vh;"
        ></el-input>
      </div>
    </el-card>
    <br/>

    <el-table :data="this.categoryListShow" :border="true" ref="messageTable" height="500">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="categoryName" label="类别名称"></el-table-column>
      <el-table-column prop="categoryRank" label="排序值"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
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


    <el-dialog :visible="dialogVisible" title="新增/编辑分类" @close="closeDialog">
      <el-form ref="editForm" :model="editForm" label-width="100px" :rules="rules">

        <el-form-item label="类别" prop="categoryName">
          <el-input v-model="editForm.categoryName"  ></el-input>
        </el-form-item>
        <el-form-item label="排序值" prop="categoryRank">
          <el-input-number v-model="editForm.categoryRank" name="categoryRank"></el-input-number>
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
  name:"categoryController",
  data() {
    return {
      rules: {
        categoryName: [
          { required: true, message: '类别不能为空', trigger: 'blur' }
        ] ,

      },
      filteredData: [], // 过滤后的数据
      selectedPublisher: '', // 当前选中
      selectedKind: '', // 当前选中
      // adminList:  [
      //   { goodId : 1,image: require('../../../assets/img/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'), goodName: 'aaa', price: 50 ,publisherId:'AAA',kind:'生活用品',postage:5,number:2,createTime:'2023-6-28 1:40'},
      // ],
      categoryList:[{},{}],
      backUrl: "http://localhost:8090/images/upload/", //回显的路径
      dialogVisible: false, // 编辑对话框可见性
      searchKeyword: '', // 搜索关键字
      editForm: {
        categoryId: 0,
        categoryName: '',
        createTime:'',
        categoryRank:'',
        isDeleted:'',
        description:'',


      },

      uploadUrl: 'http://localhost:8090/good/upload',
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      isAddingCarousel: false,

      //删除列表
      deleteList:[],
    };
  },
  beforeMount() {

    this.getCategoryData();
  },
  computed: {

    displayData() {
      return this.filteredData.length ? this.filteredData : this.categoryList;
    },
    categoryListShow() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.displayData.slice(start, end);
    },

  },
  methods: {
    getCategoryData(){
      let _this = this
      _this.$axios({
        url:this.$http + '/good-category/list',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {
        if(res.resultCode==200)
        {
          _this.categoryList = res.data;

        }
        else{
          alert("失败")
        }
      });

    },

    search(){
      // 构建正则表达式，匹配包含关键字的项
      const regex = new RegExp(this.searchKeyword, 'i');

      // 执行模糊查询
      this.filteredData = this.categoryList.filter((item) =>
          regex.test(item.goodName)
      );
      // 检查过滤后的数据长度
      if (this.filteredData.length === 0) {
        // 弹出提示框，提示未找到指定数据
        this.$message({
          type: 'warning',
          message: '未找到指定数据'
        });
      }
    },
    save(index){
      let data = this.categoryList[index];
      this.$axios({
        url:this.$http + '/good-category/saveOrModGood',
        method:'post',
        data:data,
        headers:{
          token:localStorage.getItem("token")
        }



    }).then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
          this.getCategoryData();
        }else{
          this.getCategoryData();
          alert('更新数据失败')
        }
      })
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
      // //后端
      this.$confirm('此操作将删除该商品信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //这段连接后端之后其实就没用了 但是可以显示的更快
        this.categoryList = this.categoryList.filter(good => !selectedRows.includes(good));
        for (let i = 0; i < selectedRows.length; i++) {
          this.deleteList.push(selectedRows[i].categoryId);
        }
        //后端
        this.$axios({
          url:this.$http + '/good-category/delete?goodId=' + this.deleteList,
          method:'get',
          headers:{
            token:localStorage.getItem('token')
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){
            // // console.log(132165464)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getCategoryData()
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
      if(this.editForm.categoryName==null)
      {
        this.$message.warning('请输入完整数据')
        return;
      }


      // 设置标志位为false
      this.isAddingCarousel = false;
    },
    resetForm() {
      // 重置表单
      this.editForm = {
        categoryId: 0,
        categoryName: '',
        createTime:'',
        categoryRank:'',
        isDeleted:'',
        description:'',
        // imageUrl:''

      };
    },
    closeDialog(){
      this.dialogVisible = false
      this.resetForm()
    },
    saveMessage() {
      this.$refs.editForm.validate((valid)=> {
            if (valid) {
              let newMessage = null;
              if (this.isAddingCarousel) {

                const maxId = Math.max(...this.categoryList.map(category => category.categoryId));
                const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
                let newId = 1;
                if(maxId!==-Infinity){
                  newId = maxId + 1
                }

                newMessage = {

                  categoryId: newId,
                  categoryName: this.editForm.categoryName,
                  categoryRank: this.editForm.categoryRank,
                  publisherId:this.editForm.publisherId,
                  createTime:formattedDate,
                  isDeleted:this.editForm.isDeleted,

                };
              }

              if (this.editForm.categoryRank <= 0) {
                this.$message.error("请更换排序值（不能小于等于0）")
                return;
              }
              const isRankConflict = this.categoryList.some(category => {
                return category.categoryRank=== this.editForm.categoryRank &&
                    (this.editForm.categoryId === 0 || category.categoryId !== this.editForm.categoryId);
              });
              if (isRankConflict) {
                this.$message.error('类别排序值冲突，请重新设置');
                return;
              }
              const isRankConflict2 = this.categoryList.some(category => {
                return category.categoryName=== this.editForm.categoryName &&
                    (this.editForm.categoryId === 0 || category.categoryId !== this.editForm.categoryId);
              });
              if (isRankConflict2) {
                this.$message.error('类名冲突，请重新设置');
                return;
              }
              //标记是否有新增错误
              if (this.isAddingCarousel) {
                this.categoryList.push(newMessage);
              }


              const index = this.categoryList.findIndex(category=> category.categoryId === this.editForm.categoryId);
              if (index !== -1) {
                this.categoryList.splice(index, 1, {...this.editForm});
              }



              if (this.isAddingCarousel) {
                const index = this.categoryList.length - 1
                this.save(index)
                this.$message.success('新增类别信息成功');
              } else {
                this.save(index)
                this.$message.success('编辑类别信息成功');
              }

              this.dialogVisible = false; // 关闭编辑对话框
            }
            else {
              this.$message.error('请输入完整信息'); // 表单项验证不通过，给出错误提示
            }

          }


      )

    },

    // 在页码变化时更新当前页数，并触发计算属性pagedCarouselList的重新计算
    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    },

    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize;
      // 当项数改变时，重置当前页为第一页S
      this.currentPage = 1;
    },
    handlePreview(file) {
    }

  }
};
</script>

<style scoped>
.search-container {
  display: flex;
  align-items: center;
  height: 40px;
}
</style>

