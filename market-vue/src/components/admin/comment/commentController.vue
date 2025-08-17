<template>
  <div>
    <el-card>
      <div>
        <div slot="header">
          <h2>评论管理</h2>
        </div>
        <el-divider></el-divider>
        <div class="search-container">
          <el-button type="danger" style="margin-left: 5px;" icon="el-icon-delete" @click="deleteRows">删除</el-button>
          <el-button type="success" icon="el-icon-edit" @click="showSensitiveWords">敏感词管理</el-button>
          <el-input
              v-model="searchKeyword"
              placeholder="请输入被评论人序号关键字"
              icon="el-icon-search"
              @input="search"
              style="margin-left: 5vh;"
          ></el-input>
        </div>

        <!--        敏感词管理-->
        <el-dialog
            title="敏感词管理"
            :visible.sync="showSensitiveWordsDialog"
            :append-to-body="true"
        >
          <el-card>
            <div class="button-container">
              <!-- 新增敏感词表单 -->
              <div class="details-row">

                <el-input v-model="newSensitiveWord" placeholder="请输入新增敏感词" style="margin-right: 2px"></el-input>
                <el-button type="primary" @click="addSensitiveWord">新增</el-button>

              </div>
              <!-- 删除敏感词选择框 -->
              <div class="deleteButton">
                <el-select v-model="selectedSensitiveWord" placeholder="请选择要删除的敏感词" style="margin-right: 2px">
                  <el-option v-for="word in sensitiveWords" :key="word.sensitiveId" :value="word.sensitiveId" :label="word.word"></el-option>
                </el-select>
                <el-button type="danger" @click="deleteSensitiveWord">删除</el-button>
              </div>
            </div>
          </el-card>

          <!-- 展示敏感词列表 -->
          <el-table
              :data="sensitiveWords"
              :border="true"
              ref="wordTable">
            <el-table-column prop="word" label="敏感词"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
          </el-table>
        </el-dialog>

        <br/>
      </div>
    </el-card>

    <div>
      <el-main>
        <el-table
            :data="pagedCarouselList"
            :border="true"
            height="500"
            ref="commentTable">
          <el-table-column type="selection"></el-table-column>
          <el-table-column prop="reviewerName" label="评论者姓名">
          </el-table-column>
          <el-table-column prop="reviewedName" label="被评论人姓名">
          </el-table-column>
          <el-table-column prop="responseOrdinal" label="是否回复">
            <template slot-scope="scope">
              <el-tag
                  :type="scope.row.responseOrdinal === 0 ? 'primary' : 'success'"
                  disable-transitions>{{scope.row.responseOrdinal === 0 ? '未回复' : '已回复'}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="stars" label="星级">
          </el-table-column>
          <el-table-column prop="initialComments" label="初始评论">
          </el-table-column>
          <el-table-column prop="modifiedComments" label="被屏蔽后评论">
          </el-table-column>
          <el-table-column prop="timestamp" label="评价时间">
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
  name: "commentController",
  data() {
    return {
      // tableData:[
      //   {reviewerId: 1, reviewedId: 8, responseOrdinal: 1, stars:5, initialComments:'真是垃圾！',modifiedComments:'真是买到宝了！', timestamp:'2023-6-2', twiceComment:2},
      // ],
      // sensitiveWords:[
      //   { sensitiveId:'1',word:'垃圾',createTime:'2023-6-25'},
      // ],

      //展示的数据
      tableData:[{
        commentId:0,
        reviewerName:'',
        reviewedName:'',
        responseOrdinal:0,
        stars:0,
        initialComments:'',
        modifiedComments:'',
        timestamp:'',
        twiceComment:'',
      }],//初始页面显示评论数据
      sensitiveWords:[],//敏感词页面显示敏感词

      showSensitiveWordsDialog:false,//敏感词管理界面
      searchKeyword: '', // 搜索关键字
      filteredData: [], // 过滤后的数据
      selectedRows:[],//选中的数据

      newSensitiveWord: '', // 新增的敏感词
      selectedSensitiveWord: '', // 选中的要删除的敏感词

      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      deleteList:[],
    }
  },
  methods:{
    search(){
      // 将用户输入的关键字按空格拆分为数组
      const keywords = this.searchKeyword.trim().split(' ');

      // 执行模糊查询
      this.filteredData = this.tableData.filter((item) => {
        // 对每个关键字进行匹配
        return keywords.every((keyword) => {
          const regex = new RegExp(keyword, 'i');
          return regex.test(item.reviewedId);
        });
      });

      // 检查过滤后的数据长度
      if (this.filteredData.length === 0) {
        // 弹出提示框，提示未找到指定数据
        this.$message.warning('未找到指定数据');
      }
    },
    deleteRows(){
      // 获取选中的行数据
      const selectedRows = this.$refs.commentTable.selection;
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }
      for (let i = 0; i < selectedRows.length; i++) {
        this.deleteList.push(selectedRows[i].commentId);
      }
      this.$confirm('此操作将永久删除该评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tableData = this.tableData.filter(comment => !selectedRows.includes(comment));
        this.filteredData = this.filteredData.filter(comment => !selectedRows.includes(comment));
        //后端
        this.$axios({
          url:this.$http + '/comment/deleteComment?commentId=' + this.deleteList,
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
            this.loadComment();
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
    showSensitiveWords(){
      this.showSensitiveWordsDialog=true;
    },
    addSensitiveWord() {
      if (this.newSensitiveWord.trim() === '') {
        this.$message.warning('请输入新增敏感词');
        return;
      }
      // 获取当前sensitiveWords中的最大id值，并在其基础上加1，作为新增敏感词的id
      const maxId = Math.max(...this.sensitiveWords.map(word => word.sensitiveId));
      let newId = 1;
      if(maxId!==-Infinity){
        newId = maxId + 1
      }
      // 获取新增敏感词的值
      const word = this.newSensitiveWord.trim();

      const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');

      // 判断新增敏感词名称是否与已有敏感词名称重复
      const isDuplicate = this.sensitiveWords.some(
          (item) =>
              item.word === this.newSensitiveWord.trim()
      );
      if (isDuplicate) {
        this.$message.error('敏感词重复，请输入不同的敏感词!');
      }else{
        // 添加到敏感词列表
        const  newItem={
          sensitiveId:newId,word: word, createTime: formattedDate
        }
        this.sensitiveWords.push(newItem);

        //后端操作
        this.$axios({
          url:this.$http + '/sensitive-word/add',
          data:newItem,
          method:'post',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){

            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadSensitive()
            this.loadComment()
          }else{
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }

        })
      }

      // 清空输入框
      this.newSensitiveWord = '';

    },
    deleteSensitiveWord() {
      if (this.selectedSensitiveWord) {
        // 获取选择的要删除的敏感词
        const sensitiveId = this.selectedSensitiveWord;
        // 找到要删除的敏感词在敏感词列表中的索引
        const index = this.sensitiveWords.findIndex(item => item.sensitiveId === sensitiveId);
        if (index !== -1) {
          // 从敏感词列表中移除该敏感词
          this.sensitiveWords.splice(index, 1);
          //后端
          this.$axios({
            url:this.$http + '/sensitive-word/deleteSensitive?sensitiveId=' + sensitiveId,
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
              this.loadSensitive();
              this.loadComment();
            }else{
              this.$message({
                message: '删除失败！',
                type: 'error'
              });
            }

          })
          // 清空选择的敏感词
          this.selectedSensitiveWord = '';
        }
      }else{
        this.$message.warning('请选择敏感词');
        return;
      }
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
    loadComment(){
      this.$axios({
        url:this.$http + '/comment/listCP',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
          this.tableData=res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
    loadSensitive(){
      this.$axios({
        url:this.$http + '/comment/listSP',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
          this.sensitiveWords=res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
  },
  computed:{
    displayData() {
      // 根据是否有过滤数据，动态决定显示哪个数据源
      return this.filteredData.length ? this.filteredData : this.tableData;
    },
    pagedCarouselList() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.displayData.slice(start, end);
    },
  },
  beforeMount() {
    this.loadComment()
    this.loadSensitive()
  }
}
</script>

<style scoped>
.search-container {
  display: flex;
  align-items: center;
  height: 40px;
}
.button-container {
  display: flex;
  justify-content: center; /* 将按钮水平居中对齐 */
  margin-top: 10px;
}

.details-row {
  display: flex;
  align-items: center; /* 将按钮垂直居中对齐 */
  margin-right: 10px;
}
.deleteButton{
  margin-left: 10px;
}
</style>