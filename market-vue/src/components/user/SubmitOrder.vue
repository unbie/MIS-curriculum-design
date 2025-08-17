<template>
<div>
  <div style="position: absolute; top: 0%; left: 45%; color:#21675e">
    <p >提交订单</p>
  </div>


  <img :src="backUrl + orderItem.goodImg"
       width="350" height="350" style="position: absolute; top: 10%; left: 10%">
  <div><p style="font-size: 25px; color: #2b5b55; position: absolute; top: 7%; left: 50%">
    {{ orderItem.goodName }} </p>
  </div>
  <div style="width:600px;height: 300px;border:2px dashed darkgreen;position: absolute; top: 17%; left: 45%"></div>
  <div style="position:absolute;top:16.5%;left:47%">
    <p style="margin-bottom: 27px">
      订单编号：{{orderItem.orderNo}} </p>
    <p style="margin-bottom: 27px">
      卖家账号：{{orderItem.sellerAccount}}</p>
    <p style="margin-bottom: 27px">
      买家账号：{{orderItem.customerAccount}}</p>
    <p style="margin-bottom: 27px">
      全部金额：{{orderItem.totalPrice}}</p>
    <p style="margin-bottom: 27px">
      收货地址：{{orderItem.shippingAddress}}</p>
    <p style="margin-bottom: 27px">
      收货电话：{{orderItem.shoppingPhone}}</p>
  </div>

  <div class="square" style="position: absolute; top: 60%;left:10%"><p>支付方式</p></div>
  <button @click="openWX"
          class="image-btn" style="position: absolute; top: 70%;left:10%">
    <img src="../../assets/Userimg/wechat.png" alt="Button Image">
    微信支付
  </button>
  <button @click="openZFB"
      class="image-btn" style="position: absolute; top: 70%;left:30%">
    <img src="../../assets/Userimg/alipay.png" alt="Button Image">
    支付宝支付
  </button>
  <button class="pay-btn" style="position: absolute;top:85%;left: 80%"
  @click="payLater">稍后支付</button>

  <!--微信支付-->
  <el-dialog :visible.sync="WXVisible" title="微信支付中">
    <img src="../../assets/Userimg/WXpaid.png" :style="{ width: '300px', height: 'auto'}">
    <span slot="footer" class="dialog-footer">
        <el-button @click="WXVisible = false">取消支付</el-button>
        <el-button type="primary" @click="WXConfirm">已完成支付</el-button>
      </span>
  </el-dialog>
  <!--  支付宝支付  -->
  <el-dialog :visible.sync="ZFBVisible" title="支付宝 支付中">
    <img src="../../assets/Userimg/WXpaid.png" :style="{ width: '300px', height: 'auto'}">
    <span slot="footer" class="dialog-footer">
        <el-button @click="ZFBVisible = false">取消支付</el-button>
        <el-button type="primary" @click="ZFBConfirm">已完成支付</el-button>
      </span>
  </el-dialog>


</div>
</template>
<script>
export default {
  name: 'OrderCenter',
  data(){
    return{
      WXVisible:false,
      ZFBVisible:false,
      AddressDialogVisible:false,
      TelephoneDialogVisible:false,
      AddressForm: {
        address: ''
      },
      TelephoneForm: {
        telephone: ''
      },
      activeNumber:1,

      message:{
        good:'某某牌中性笔',
        id:123456,
        name:'小明',
        name2:'小花',
        money:20,
        telephone:'123 4567 1111',
        address:'武汉大学信息学部',
      },

      orderItem:{},
      backUrl: "http://localhost:8090/images/upload/", //回显的路径
    }
  },
  methods:{
    //稍后支付
    payLater(){
      this.$router.push('/user/OrderCenter')//跳转到订单中心
    },
    openWX()
    //打开微信支付
    {
      this.WXVisible=true;
    },
    WXConfirm()
    {
      // 后端
      // 改写订单状态
      // 将订单状态改写为已支付 支付方式改写为微信支付 支付状态改写为支付成功 支付时间为当前时间
      this.update(2)

      this.WXVisible = false;
      this.$message.success('支付成功');
      this.$router.push('/user/HomeOne')//跳转到主页面
    },

    openZFB()
    //打开支付宝支付
    {
      this.ZFBVisible=true;
    },
    ZFBConfirm()
    {
      //后端更新操作
      this.update(1)

      this.ZFBVisible = false;
      this.activeNumber++;
      this.$message.success('支付成功');
      this.$router.push('/user/HomeOne')//跳转到主页面
    },

    //选择更新
    update(payWay){
      this.$axios({
        url:this.$http + '/order/updateStatus',
        method:'post',
        data:{
          orderId:this.$route.query.orderId,
          orderStatus:1,
          payStatus:1
        },
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode!==200){
          alert('更新数据失败')
        }
      });
      this.$axios({
        url:this.$http + '/order/updatePayWays',
        method:'post',
        data:{
          orderId:this.$route.query.orderId,
          payWay:payWay,
        },
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.activeNumber += 1;
        }else{
          alert('更新数据失败')
        }
      });
    },

    // 更改地址
    saveAddress() {
      this.message.address=this.AddressForm.address;
      this.$message.success('更改地址成功');
      this.AddressDialogVisible = false; // 关闭编辑对话框
    },
    openTelephoneDialog() {
      this.TelephoneDialogVisible = true;
      this.resetForm()//重置数据
    },
    //获取当前orderId所对应的订单详情
    loadOrder(){
      this.$axios({
        url:this.$http + '/order/getOrderInfo?orderId='+this.orderItem.orderId,
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.orderItem=res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
  },
  beforeMount() {
    this.loadOrder();
  },
  created() {
    this.orderItem.orderId = this.$route.query.orderId
  }
}
</script>

<style scoped>

.small-btn{
  border-radius: 10px;
  background-color: #2D8578;
  color: white;
  padding: 8px 15px;
  font-size: 12px;
  cursor: pointer;
}
.small-btn:hover{
  background-color: #2b5b55;
}
.square {
  width: 100px;
  height: 50px;
  border-radius: 10px;
  background-color: #b3d1b1;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  color: black;
}
.square p {
  color: white;
  font-size: 16px;
  font-weight: bold;
  margin: 0;
}


.image-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  background-color: #f0f0ed;
  color: black;
  border: none;
  border-radius: 5px;

}
.image-btn img {
  width: 100px;
  height: 100px;
  margin-right: 20px;
}
.image-btn:hover{
  background-color: #e1e1db;
}

.pay-btn {
  border-radius: 10px;
  background-color: #2D8578;
  color: white;
  border: none;
  padding: 10px 25px;
  font-size: 16px;
  cursor: pointer;
}
.pay-btn:hover {
  background-color: #2b5b55;
}

</style>