<template>
  <div>
    <div style="position:absolute;top:10%;left:5%;">
      <button class="btn"  @click="goback()">
        <i class="el-icon-back"> 返回</i></button>
    </div>
    <el-table :data="leaveMessages" :border="true" style="margin-top: 7vh">
      <el-table-column label="用户昵称">
        <template slot-scope="scope">
          {{ scope.row.reviewerName }}
        </template>
      </el-table-column>
      <el-table-column label="留言信息">
        <template slot-scope="scope">
          {{ scope.row.content }}
        </template>
      </el-table-column>
      <el-table-column label="留言时间">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column label="商品名称">
        <template slot-scope="scope">
          {{ scope.row.goodName }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="toGood(scope.row.goodId)">前往</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
export default {
  name: "MessageCenter",
  data() {
    return {
      leaveMessages: [],
      currentUser: '',

    }
  },
  methods: {
    goback() {
      this.$router.go(-1); // 返回上一个页面
    },

    getLeaveMessages(personId) {
      let _this = this
      this.$axios({
        url: this.$http + '/leave-messages/checkMes?personId=' + personId,
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {

        if (res.resultCode == 200) {
          _this.leaveMessages = res.data;
        } else {
          alert("失败")
        }
      })

    },

    //跳转到商品界面
    toGood(goodId) {
      this.$router.push({
        path: '/user/GoodInformation',
        query: {productId: goodId}
      })
    }

  },




  mounted() {


    this.getLeaveMessages(this.currentUser.personId);
  },
  beforeMount() {

    this.currentUser = JSON.parse(localStorage.getItem("person"));

  }}

</script>

<style scoped>
.btn {
  font-size: 18px;
  color:white;
  border-radius:150px;
  height:40px;
  padding: 0 35px;
  background-color: #2d8578;
  border: 1px solid #2D8578;
  line-height: 1.2;
  text-align: center;
  cursor: pointer;
  transition: opacity 0.2s;
  outline: none;
  position: relative;
}

.btn::before {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  border-radius:15px;
  background-color: #000;
  border: inherit;
  transform: translate(-50%, -50%);
  opacity: 0;
  content: ' ';
}
.btn:active::before {
  opacity: 0.1;
}

</style>