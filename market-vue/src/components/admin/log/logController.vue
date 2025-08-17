<template>
  <div>
    <el-card class="custom-card">
      <div>
        <div slot="header">
          <h2>日志表单</h2>
        </div>
        <el-divider></el-divider>
        <div class="search-container">
          <el-button type="danger" icon="el-icon-delete" @click="deleteRows">删除</el-button>
          <el-form :inline="true" class="search-form" @submit.native.prevent="searchLogs">
            <el-form-item label="日期">
              <el-date-picker v-model="searchDate" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="登录用户名">
              <el-input v-model="searchUsername" placeholder="输入登录用户名"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" native-type="submit">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
        <br/>
      </div>
    </el-card>

    <div>
      <el-main>
        <el-table
            :data="pagedCarouselList"
            :border="true"
            height="500"
            ref="logTable">
          <el-table-column type="selection"></el-table-column>
          <el-table-column prop="logId" label="日志序号">
          </el-table-column>
          <el-table-column prop="userAccount" label="用户账号">
          </el-table-column>
          <el-table-column prop="logContent" label="操作内容">
          </el-table-column>
          <el-table-column prop="ipAddress" label="用户登录的IP地址">
          </el-table-column>
          <el-table-column prop="os" label="用户电脑操作系统">
          </el-table-column>
          <el-table-column prop="browser" label="用户登录的浏览器">
          </el-table-column>
          <el-table-column prop="createdate" label="创建时间">
          </el-table-column>
          <el-table-column prop="remark" label="备注">
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
  name: "logController",
  data(){
    return{
      tableData:[],
      filteredData:[],

      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      searchDate: '', // 新增的属性，用于存储查询的日期
      searchUsername: '', // 新增的属性，用于存储查询的用户名

      deleteList:[],
    }
  },
  methods:{
    searchLogs(){
      // 构建日期和用户名的正则表达式，匹配包含关键字的项
      // 获取日期和用户名的查询条件
      const searchDate = this.searchDate;
      const searchUsername = this.searchUsername;

      // 执行查询
      this.filteredData = this.tableData.filter((item) => {
        if (searchDate && searchUsername) {
          // 同时输入了日期和用户名
          const dateMatch = item.createdate && this.$moment(item.createdate).isSame(searchDate, 'day');
          const usernameMatch = item.userAccount && item.userAccount.includes(searchUsername);
          return dateMatch && usernameMatch;
        } else if (searchDate) {
          // 只输入了日期
          return item.createdate && this.$moment(item.createdate).isSame(searchDate, 'day');
        } else if (searchUsername) {
          // 只输入了用户名
          return item.userAccount && item.userAccount.includes(searchUsername);
        }
        return true; // 没有输入任何查询条件时返回所有数据
      });

      // 检查过滤后的数据长度
      if (this.filteredData.length === 0) {
        // 弹出提示框，提示未找到指定数据
        this.$message({
          type: 'warning',
          message: '未找到指定数据'
        });
      }
    },
    loadLog(){
      this.$axios({
        url:this.$http + '/sys-log/listLog',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.tableData=res.data
        }else{
          alert('获取数据失败')
        }
      })
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
    deleteRows(){
      // 获取选中的行数据
      const selectedRows = this.$refs.logTable.selection;
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }
      for (let i = 0; i < selectedRows.length; i++) {
        this.deleteList.push(selectedRows[i].logId);
      }
      this.$confirm('此操作将永久删除该日志, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tableData = this.tableData.filter(Syslog => !selectedRows.includes(Syslog));
        //调用后端
        this.$axios({
          url:this.$http + '/sys-log/delete?logId=' + this.deleteList,
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
    this.loadLog();
  }
}
</script>

<style scoped>
.custom-card{
  background-color: #a2d588;
}

.search-container {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.search-form {
  display: flex;
  align-items: center;
  margin-left: 10px;
  margin-top: 22px;
}

.search-form .el-form-item {
  margin-right: 10px;
}

.search-form .el-button {
  margin-left: 10px;
}
</style>