<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>求购信息管理</h2>
      </div>
      <div class="search-container">
        <el-button type="danger" icon="el-icon-delete" @click="deleteMessage">删除</el-button>
        <el-button type="success" icon="el-icon-edit" @click="editMessage">编辑</el-button>
        <el-input
            v-model="searchKeyword"
            placeholder="请输入商品名关键字"
            icon="el-icon-search"
            @input="search"
            style="margin-left: 5vh;"
        ></el-input>
      </div>
    </el-card>
    <br/>

    <el-table :data="this.goodPropertyListShow" :border="true" ref="messageTable" height="500">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="image" label="商品">
        <template v-slot:default="scope">

          <viewer>
            <img v-for="(img,index) in scope.row.image" :src="backUrl+img" alt="商品" style="height:6vw; width:6vw" :key="index">
            <img v-if="goodPropertyList.image===null" :src="backUrl+defaultImg" style="height:6vw; width:6vw">
          </viewer>
        </template>
      </el-table-column>
      <el-table-column prop="goodName" label="商品名称"></el-table-column>
      <el-table-column prop="price" label="商品价格"></el-table-column>
      <el-table-column prop="description" label="商品简介"></el-table-column>
      <el-table-column prop="publisherId" label="发布者"></el-table-column>
      <el-table-column prop="categoryId" label="类别"></el-table-column>
      <el-table-column prop="counts" label="数量"></el-table-column>
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


    <el-dialog :visible="dialogVisible" title="新增/编辑求购信息" @close="closeDialog">
      <el-form ref="editForm" :model="editForm" label-width="100px"  class="config-form" :rules="rules">
        <el-form-item label="商品图片">
          <el-upload
              class="avatar-uploader"
              :action="uploadUrl"
              :file-list="goodPropertyList.image"
              list-type="picture-card"
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
              multiple
              :on-remove="handleRemove"
              :limit="5"
          >
            <img v-for="(img,index) in goodPropertyList.image" :src="backUrl+img" alt="商品" style="height:6vw; width:6vw" :key="index">
          </el-upload>
        </el-form-item>
        <el-form-item label="商品名称" prop="goodName">
          <el-input v-model="editForm.goodName" name="goodName"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model="editForm.price" name="price"></el-input>
        </el-form-item>
        <el-form-item label="商品简介" prop="description">
          <el-input v-model="editForm.description" name="description"></el-input>
        </el-form-item>
        <el-form-item  label="类别">
          <el-select v-model="editForm.categoryId" placeholder="请选择商品类别">
            <!-- 默认选项 -->
            <el-option label="请选择商品类别" value=""></el-option>
            <el-option
                v-for="category in categoryList"
                :key="category.categoryId"
                :label="category.categoryName"
                :value="category.categoryId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="editForm.counts" name="counts"></el-input>
        </el-form-item>
        <el-form-item label="商品发布者">

          <el-select v-model="editForm.publisherId" name="publisherId" placeholder="请选择商品发布者">
            <!-- 默认选项 -->
            <el-option label="请选择商品发布者" value=""></el-option>
            <!-- 学校选项 -->
            <el-option
                v-for="publisher in publisherList"
                :key="publisher.personId"
                :label="publisher.userName"
                :value="publisher.personId"
            ></el-option>
          </el-select>
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
  name:"goodController",
  data() {
    return {
      defaultImg:'1688194482436logo.jpg',
      //uuid:null,
      test:'ssssss',
      rules: {
        goodName: [
          { required: true, message: '商品不能为空', trigger: 'blur' }
        ] ,
        price: [
          { required: true, message: '价格不能为空', trigger: 'blur' }
        ]
      },
      filteredData: [], // 过滤后的数据
      selectedPublisher: '', // 当前选中
      selectedKind: '', // 当前选中
      //selectedPublisher: '', // 当前选中
      //kindList:[{kind_id:1,kind_name:'生活用品'} ,{kind_id:2,kind_name:'电子产品'}],
      categoryList:[],
      publisherList: [
      ],
      // publisherList: [
      //
      //   ],

      // adminList:  [
      //   { goodId : 4,image: require('../../../assets/img/upload/0a592388-1535-4f9f-8201-ecb78c48bb3d.jpg'), goodName: 'eee', price: 50 ,publisherId:'AAA',kind:'电子产品',postage:5,number:2,createTime:'2023-6-28 1:40'},
      // ],
      goodPropertyList:[{},{}],
      backUrl: "http://localhost:8090/images/upload/", //回显的路径
      dialogVisible: false, // 编辑对话框可见性
      searchKeyword: '', // 搜索关键字
      editForm: {
        goodId: 0,
        image: [],
        //publisherId: '',
        publisherId:'',
        price: '',
        categoryId:'',
        expressPrice:'',
        counts:1,
        createTime:'',
        isSale:0,
        description:'',
        //imageUrl:''


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

    this.getGoodData();
    this.getCategories();
    this.getPublishers();
  },
  computed: {

    getPublisherName(id) {
      return this.publisherList.filter((item)=>{
        if(item.id===id)
          return item;
      })
    },
    displayData() {
      // console.log("商品");
      // console.log(this.goodPropertyList[0]);
      return this.filteredData.length ? this.filteredData.filter((item)=>
      {
        if(item.isSale===0) return item;
      }) : this.goodPropertyList.filter((item)=>
      {
        if(item.isSale===0) return item;
      });
    },
    goodPropertyListShow() {
      // console.log(this.goodPropertyList);
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.displayData.slice(start, end);
    },

  },
  methods: {
    getGoodData(){
      let _this = this
      this.$axios({
        url:this.$http + '/good/list',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {
          _this.goodPropertyList = res.data;
        }
        else{
          alert("失败")
        }
      });

    },


    getCategories(){
      let _this = this
      this.$axios({
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

    getPublishers(){
      let _this = this
      this.$axios({
        url:this.$http + '/person/listP',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {
          _this.publisherList = res.data;
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
      this.filteredData = this.goodPropertyList.filter((item) =>
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
      let data = this.goodPropertyList[index];
      this.$axios({
        url:this.$http + '/good/saveOrModGood',
        data:data,
        method:'post',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
          this.getGoodData();
        }else{
          this.getGoodData();
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
        this.goodPropertyList = this.goodPropertyList.filter(good => !selectedRows.includes(good));
        for (let i = 0; i < selectedRows.length; i++) {
          this.deleteList.push(selectedRows[i].goodId);
        }
        //后端
        this.$axios({
          url:this.$http + '/good/deleteGoods?goodId=' + this.deleteList,
          method:'get',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){
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
      if(this.editForm.goodName==null||this.editForm.price==null)
      {
        this.$message.warning('请输入完整数据')
        // console.log('请输入完整数据')
        return;
      }


      // 设置标志位为false
      this.isAddingCarousel = false;
    },
    resetForm() {
      // 重置表单
      this.editForm = {
        goodId:'',
        image:[],
        goodName: '',
        price: '',
        //publisherId: '',
        publisherId:'',
        categoryId:'',
        expressPrice:'',
        counts:1,
        createTime:'',
        isSale:0,
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
                const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
                const maxId = Math.max(...this.goodPropertyList.map(good => good.goodId));
                let newId = 1;
                if(maxId!==-Infinity){
                  newId = maxId + 1
                }
                newMessage = {
                  goodId: newId,
                  //测试数据image
                  //image:this.editForm.image,
                  image: this.editForm.image,
                  goodName: this.editForm.goodName,
                  price: this.editForm.price,
                  // publisherId: this.editForm.publisherId,
                  publisherId:this.editForm.publisherId,
                  categoryId:this.editForm.categoryId,
                  expressPrice:this.editForm.expressPrice,
                  counts:this.editForm.counts,
                  createTime:formattedDate,
                  isSale:0,
                  description:this.editForm.description,
                  //imageUrl:this.editForm.imageUrl
                };

              }

              if (this.isAddingCarousel) {
                this.goodPropertyList.push(newMessage);
              }

              const index = this.goodPropertyList.findIndex(carousel => carousel.goodId === this.editForm.goodId);
              if (index !== -1) {
                this.goodPropertyList.splice(index, 1, {...this.editForm});
              }



              if (this.isAddingCarousel) {
                const index = this.goodPropertyList.length - 1
                this.save(index)
                this.$message.success('新增商品信息成功');
              } else {
                this.save(index)
                this.$message.success('编辑商品信息成功');
              }

              this.dialogVisible = false; // 关闭编辑对话框
            }
            else {
              this.$message.error('请输入完整信息'); // 表单项验证不通过，给出错误提示
            }

          }


      )

    },
    handleUploadSuccess(response) {
      // 处理图片上传成功回调 ？
      // 获取上传成功后的图片地址，并赋值给表单数据 ？
      this.editForm.image.push(response.data);
    },
    beforeUpload(file) {
      // 图片上传前的校验，例如图片类型、大小等
      // 返回 false 可取消上传
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('只允许上传 JPG/PNG 格式的图片');
        return false;
      }

      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB');
        return false;
      }
      return true;
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
    // eslint-disable-next-line no-unused-vars
    handleRemove(file,fileList)
    {

    },
    // eslint-disable-next-line no-unused-vars
    handlePreview(file) {
      // console.log(file);
    }

  },


};
</script>

<style scoped>
.search-container {
  display: flex;
  align-items: center;
  height: 40px;
}
.carousel-item-text {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  padding: 16px;
  box-sizing: border-box;
}

.carousel-item-text h3 {
  margin-top: 0;
  margin-bottom: 8px;
}

.carousel-item-text p {
  margin-top: 0;
  margin-bottom: 0;
}
</style>

