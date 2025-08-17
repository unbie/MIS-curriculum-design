<template>
  <div>
    <!-- 数据统计-->
    <el-row :gutter="10">
      <el-col :span="8">
        <div class="statistics" style="background:#409eff" >
          <div class="title">
            <p>本日交易额</p>
            <el-tag size="small" type="success">实时</el-tag>
          </div>
          <div class="num">{{ dailyTurnover }}</div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="statistics" style="background:#E6A23C" >
          <div class="title">
            <p>本月交易额</p>
            <el-tag size="small" type="info">实时</el-tag>
          </div>
          <div class="num">{{ monthTurnover }}</div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="statistics" style="background:#67C23A" >
          <div class="title">
            <p>年度交易额</p>
            <el-tag size="small" type="warning">实时</el-tag>
          </div>
          <div class="num">{{ annualTurnover }}</div>
        </div>
      </el-col>
    </el-row>
    <br>
    <div class="button-wrapper">
      <el-button class="fetch-button" @click="handleButtonClick">获取数据</el-button>
    </div>
    <br>
    <br>
    <AnalysisMap></AnalysisMap>

  </div>
</template>

<script>
import AnalysisMap from "@/components/admin/AnalysisMap";

export default {
  name: 'DataAnalyse',
  components:{
    AnalysisMap
  },
  data() {
    return {
      dailyTurnover:0,
      monthTurnover:0,
      annualTurnover:0,
    }
  },
  methods: {
    // 获取后台传来的交易额数据
    fetchData: function() {
      this.$axios({
        url:this.$http + '/order/dailyTransactionAmount',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      })
          .then(res => res.data)
          .then(res => {
            if (res.resultCode === 200) {
              this.dailyTurnover = res.data;
            } else {
              alert('获取数据失败');
            }
          });

      this.$axios({
        url:this.$http + '/order/monthlyTransactionAmount',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      })
          .then(res => res.data)
          .then(res => {
            if (res.resultCode === 200) {
              this.monthTurnover = res.data;
            } else {
              alert('获取数据失败');
            }
          });

      this.$axios({
        url:this.$http + '/order/annualTransactionAmount',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      })
          .then(res => res.data)
          .then(res => {
            if (res.resultCode === 200) {
              this.annualTurnover = res.data;
            } else {
              alert('获取数据失败');
            }
          });
    },

    handleButtonClick() {
      this.fetchData();
    },
  },

  created() {
    this.fetchData(); // 页面加载时调用 fetchData 方法获取数据
  },


}
</script>

<style lang="less" scoped>
.el-row{
  padding: 0 9px;
  .statistics{
    color: white;
    height: 100px;
    border-radius:4px;
    padding: 0 8px;
    .title{
      display: flex; // 弹性布局
      justify-content: space-between; // 两边对齐
      align-items: center; // 垂直居中
    }
    p{
      font-size: 15px;
      font-weight: bold;
    }
    .num{
      font-size: 30px;
      font-weight: bold;
      text-align: center;
    }
  }
}
.button-wrapper {
  position: absolute;
  bottom: 20px;
  right: 20px;
}

</style>


