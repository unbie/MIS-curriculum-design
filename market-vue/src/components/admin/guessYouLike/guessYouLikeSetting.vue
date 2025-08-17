<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>猜你喜欢管理</h2>
      </div>
      <div>
        <el-button type="primary" icon="el-icon-circle-plus" @click="addCarousel">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="deleteCarousel">删除</el-button>
        <el-button type="success" icon="el-icon-edit" @click="editCarousel()">编辑</el-button>
      </div>
    </el-card>
    <br/>

    <!--      carouselTable 选中的轮播图列表-->
    <el-table :data="pagedCarouselList" :border="true" ref="carouselTable" height="500">
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="image" label="轮播图">
        <template slot-scope="scope">
          <img :src="scope.row.image" alt="轮播图" style="height: 64px; width: 64px" />
        </template>
      </el-table-column>
      <el-table-column prop="redirectUrl" label="跳转链接"></el-table-column>
      <el-table-column prop="carouselRank" label="排序值"></el-table-column>
      <el-table-column prop="addTime" label="添加时间"></el-table-column>
    </el-table>

    <!--      page-sizes 可选择的每页项数 -->
    <!--       layout 布局包括项数选择、上一页、页码、下一页、跳转 -->
    <!--      @size-change 项数选择改变时的事件-->
    <!--      current-change 页码改变时的事件-->
    <el-pagination
        :current-page="currentPage"
        :page-sizes="[2, 5, 10]"
        :page-size="pageSize"
        :total="carouselList.length"
        layout="sizes, prev, pager, next, jumper"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"
    ></el-pagination>


    <el-dialog :visible="dialogVisible" title="轮播图配置" @close="closeDialog">
      <el-card>
        <el-form ref="editForm" :model="editForm" label-width="100px">
          <el-form-item label="轮播图">
            <el-upload
                class="avatar-uploader"
                :action="uploadUrl"
                :show-file-list="false"
                :on-success="handleUploadSuccess"
                :before-upload="beforeUpload"
            >
              <img v-if="editForm.image" :src="editForm.image" class="avatar" alt="轮播图" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="跳转链接">
            <el-input v-model="editForm.redirectUrl" name="redirectUrl"></el-input>
          </el-form-item>
          <el-form-item label="排序值">
            <el-input-number v-model="editForm.carouselRank" name="carouselRank"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveCarousel">确认</el-button>
        </div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name:"guessYouLikeSetting",
  data() {
    return {
      carouselList: [
        // 初始化轮播图列表数据
        { id : 1,image: require('../../../../../market/src/main/resources/static/images/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'), redirectUrl: 'https://example.com', carouselRank: 1 },
      ],
      dialogVisible: false, // 编辑轮播图对话框可见性
      editForm: {
        id: 0,
        image: '',
        redirectUrl: '',
        carouselRank: 0
      },
      uploadUrl: 'your-upload-url',
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      isAddingCarousel: false,

    };
  },
  computed: {
    pagedCarouselList() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.carouselList.slice(start, end);
    },
  },
  methods: {
    addCarousel() {
      // 新增轮播图
      this.dialogVisible = true; // 显示对话框
      this.resetForm(); // 重置表单数据
      this.isAddingCarousel = true; // 设置标志位为true
    },
    deleteCarousel() {
      // 删除轮播图
      const selectedRows = this.$refs.carouselTable.selection; // 获取选中的行数据

      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }

      this.carouselList = this.carouselList.filter(carousel => !selectedRows.includes(carousel));
      //后端
      this.$message.success('删除成功');
    },
    editCarousel() {
      // 获取选中的行数据
      const selectedRows = this.$refs.carouselTable.selection;

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
        image: '',
        redirectUrl: '',
        carouselRank: 0
      };
    },
    closeDialog(){
      this.dialogVisible = false
      this.resetForm()
    },
    saveCarousel() {
      let newCarousel = null;
      if (this.isAddingCarousel) {
        //向carouselList中插入新数据
        // 获取当前carouselList中的最大id值，并在其基础上加1，作为新增轮播图的id
        const maxId = Math.max(...this.carouselList.map(carousel => carousel.id));
        const newId = maxId + 1;
        // 创建一个新的轮播图对象，包含新增轮播图的信息
        newCarousel = {
          id: newId,
          //测试数据image
          image: require('../../../../../market/src/main/resources/static/images/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg'),
          redirectUrl: this.editForm.redirectUrl,
          carouselRank: this.editForm.carouselRank
        };
      }

      // 判断编辑的轮播图的carouselRank是否与表中其他轮播图的carouselRank冲突
      const isRankConflict = this.carouselList.some(carousel => {
        return carousel.carouselRank === this.editForm.carouselRank &&
            (this.editForm.id === 0 || carousel.id !== this.editForm.id);
      });
      if (isRankConflict) {
        this.$message.error('轮播图排序值冲突，请重新设置');
        return;
      }

      if (this.isAddingCarousel) {
        // 向carouselList数组中插入新增轮播图数据
        this.carouselList.push(newCarousel);
      }


      //---------------------------
      // 更新对应轮播图的数据
      const index = this.carouselList.findIndex(carousel => carousel.id === this.editForm.id);
      if (index !== -1) {
        this.carouselList.splice(index, 1, { ...this.editForm });
      }
      //---------------------------


      // 重新获取轮播图列表数据，更新页面显示
      if (this.isAddingCarousel) {
        // 执行新增轮播图的保存逻辑...
        this.$message.success('新增轮播图成功');
      } else {
        // 执行编辑轮播图的保存逻辑...
        this.$message.success('编辑轮播图成功');
      }

      this.dialogVisible = false; // 关闭编辑对话框
    },
    handleUploadSuccess(response) {
      // 处理图片上传成功回调 ？
      // 获取上传成功后的图片地址，并赋值给表单数据 ？
      this.editForm.image = response.data.imageUrl;
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
      // 当项数改变时，重置当前页为第一页
      this.currentPage = 1;
    },
  }
};
</script>



<style scoped>

</style>