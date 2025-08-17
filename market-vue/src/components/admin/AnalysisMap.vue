<template>
  <div class="chart-container">
    <div class="chart-item">
      <el-card>
        <h2>每月交易金额</h2>
        <div id="daily-chart" class="chart"></div>
      </el-card>
   </div>

    <div class="chart-item">
      <el-card>
        <h2>年度交易额</h2>
        <div id="annual-chart" class="chart"></div>
      </el-card>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name:"AnalysisMap",
  data(){
    return{
      dailyData:[],
      annualData:[],
    }
  },
  mounted() {
    // // 模拟数据
    // this.dailyData = [
    //   { date: '2023-01-01', amount: 100 },
    // ];
    //
    // this.annualData = [
    //   { month: 'January', amount: 1200 },
    // ];
    this.fetchData()


  },
  methods: {
    // 从后台获取数据
    fetchData() {
      this.$axios({
        url:this.$http + '/order/dailyTransactionAmountList',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      })
          .then(res => res.data)
          .then(res => {
            if (res.resultCode === 200) {
              this.dailyData = res.data;
              // 按日期排序
              this.dailyData.sort((a,b) => new Date(a.date) - new Date(b.date))
              // 绘制每日交易金额折线图
              this.drawDailyChart(this.dailyData);
            } else {
              alert('获取数据失败');
            }
          });

      this.$axios({
        url:this.$http + '/order/monthlyTransactionAmountList',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      })
          .then(res => res.data)
          .then(res => {
            if (res.resultCode === 200) {
              this.annualData = res.data;
              // 按照 month 字段排序
              this.annualData.sort((a, b) => {
                const months = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
                const monthA = months.indexOf(a.month);
                const monthB = months.indexOf(b.month);
                return monthA - monthB;
              });

              // 绘制年度交易额饼图
              this.drawAnnualChart(this.annualData);
            } else {
              alert('获取数据失败');
            }
          });
    },

    drawDailyChart(data) {
      const chartDom = document.getElementById('daily-chart');
      const myChart = echarts.init(chartDom);

      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
          },
        },
        xAxis: {
          type: 'category',
          data: data.map(item => item.date),
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            type: 'line',
            data: data.map(item => item.amount),
          },
        ],
      };

      myChart.setOption(option);
    },
    drawAnnualChart(data) {
      const chartDom = document.getElementById('annual-chart');
      const myChart = echarts.init(chartDom);

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}'+'月'+': {c} ({d}%)',
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: data.map(item => ({
              name: item.month,
              value: item.amount,
            })),
          },
        ],
      };

      myChart.setOption(option);
    },
  },
};
</script>

<style scoped>
h2 {
  margin-top: 20px;
}
.chart-container {
  display: flex;/* 使用 flex 布局将图表项并排显示 */
  justify-content: space-between;/* 在容器内均匀分布图表项 */
}

.chart-item {
  flex-basis: 48%;/* 控制每个图表项的宽度比例 */
}

.chart {
  width: 100%;/* 图表宽度占满父容器 */
  height: 300px;/* 图表高度 */
}
</style>
