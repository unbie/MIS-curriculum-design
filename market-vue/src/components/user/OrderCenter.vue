<template>
  <div>
    <div>
      <input v-model="selectedOption" type="radio" id="sell" value="sell" name="sort"><label for="sell">出售</label>
      <input v-model="selectedOption" type="radio" id="buy" value="buy" name="sort"><label for="buy">购买</label>
    </div>


      <el-table :data="buyOrders" v-if="selectedOption==='buy'" :border="true" style="margin-top: 5vh">
        <el-table-column label="订单编号">
          <template slot-scope="scope">
            {{ scope.row.orderNo }}
          </template>
        </el-table-column>
        <el-table-column label="商品图片">
          <template slot-scope="scope">
            <img :src="backUrl+scope.row.imgUrl" :width="150" :height="150">
          </template>
        </el-table-column>
        <el-table-column label="商品简介">
          <template slot-scope="scope">
            {{ scope.row.description }}
          </template>
        </el-table-column>
        <el-table-column label="支付金额">
          <template slot-scope="scope">
            {{ scope.row.totalPrice }}
          </template>
        </el-table-column>
        <el-table-column label="订单状态">
          <template slot-scope="scope">
            {{ getOrderStatusText[scope.row.orderStatus] }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="detail(scope.row.orderId)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-table :data="sellOrders" v-if="selectedOption==='sell'" :border="true" style="margin-top: 5vh">
        <el-table-column label="订单编号">
          <template slot-scope="scope">
            {{ scope.row.orderNo }}
          </template>
        </el-table-column>
        <el-table-column label="商品图片">
          <template slot-scope="scope">
            <img :src="backUrl+scope.row.imgUrl" :width="150" :height="150">
          </template>
        </el-table-column>
        <el-table-column label="商品简介">
          <template slot-scope="scope">
            {{ scope.row.description }}
          </template>
        </el-table-column>
        <el-table-column label="支付金额">
          <template slot-scope="scope">
            {{ scope.row.totalPrice }}
          </template>
        </el-table-column>
        <el-table-column label="订单状态">
          <template slot-scope="scope">
            {{ getOrderStatusText[scope.row.orderStatus] }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
              <el-button type="text" @click="detail(scope.row.orderId)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>



  </div>
</template>

<script>

export default {
  name: 'OrderCenter',
  components: {},
  data() {
    return {
      selectedOption: 'buy',

      buyOrders:[],
      sellOrders:[],

      backUrl:"http://localhost:8090/images/upload/",

    }
  },
  methods: {
    detail(orderId) {
      this.$router.push({
        path: '/user/OrderNei',
        query: { orderId: orderId}
      });
    },

    loadOrdersByCustomerId(){
      this.$axios({
        url:this.$http + '/order/getOrdersByCustomerId?customerId='+JSON.parse(localStorage.getItem("person")).personId,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.buyOrders = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },

    loadOrdersBySellerId(){
      this.$axios({
        url:this.$http + '/order/getOrdersBySellerId?seller='+JSON.parse(localStorage.getItem("person")).personId,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.sellOrders = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },

  },

  computed: {
    getOrderStatusText() {
      return {
        0: '待支付',
        1: '已支付',
        2: '出库成功',
        3: '交易成功',
        '-1': '管理员关闭',
        '-2': '商家关闭',
        '-3': '买家关闭'
      };
    },
  },

  mounted() {
    this.loadOrdersByCustomerId()
    this.loadOrdersBySellerId()
  }
}


</script>

<style scoped>
input[type="radio"] + label, input[type="checkbox"] + label {

  background: linear-gradient(#bddbce 10%, #8cc0aa 50%, #bddbce);
  border-radius: 10px;
  padding: 0 10px;
  margin-left: 15px;
  border: solid 2px darkseagreen;
  color: white;
}

input[type="radio"]:checked + label, input[type="checkbox"]:checked + label {
  color: white;
  background: linear-gradient(#48846a 10%, #6bae91 50%, #48846a);
}

input[type="radio"], input[type="checkbox"] {
  display: none;
}

table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  text-align: left;
  padding: 20px;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
}

button {
  background-color: #2D8578;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 10px;
  margin-right: 10px;
}

button:hover {
  background-color: #2b5b55;
}


</style>