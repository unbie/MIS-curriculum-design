<template>
  <div>
    <p style="color:#d9e8d8; position: absolute;top:10%;left:15%;font-size: 1vw">——————————————————————————</p>
    <p style="color:#d9e8d8; position: absolute;top:10%;left:57%;font-size: 1vw">——————————————————————————</p>

    <p style="color:#21675e;position: absolute;top:7%;left:46%;font-size: 2vw">订单详情</p>
    <p style="color: #c6ddc4; position: absolute;top:13%;left:44%;font-size: 1.5vw">Order Information</p>
    <img :src="this.backUrl + message.goodImg"
         width="350" height="350" style="position: absolute; top: 22%; left: 10%">
    <div>
      <p style="font-size: 1.5vw; color: #2b5b55; position: absolute; top: 15%; left: 65%"> {{ message.goodName }}</p></div>
    <div
        style="width:40.25vw;height: 22.229vw;border:0.104vw dashed darkgreen;position: absolute; top: 22%; left: 50%"></div>
    <div style="position:absolute;top:22.5%;left:52%">
      <!--      表格-->
      <p id="id"
         style="margin-top: 1.40625vw">订单编号：{{ message.orderNo }}</p>
      <p id="name1"
         style="margin-top: 1.40625vw">卖家账户：{{ message.sellerAccount }}</p>
      <p id="name2"
         style="margin-top: 1.40625vw">买家账户：{{ message.customerAccount }}</p>
      <p id="money"
         style="margin-top: 1.40625vw">支付金额：{{ message.totalPrice }}</p>
      <p id="address"
         style="margin-top: 1.40625vw">收货地址：{{ message.shippingAddress }}</p>
      <p id="telephone"
         style="margin-top: 1.40625vw">收货电话：{{ message.shoppingPhone }}</p>
      <p id="comment" style="margin-top: 1.40625vw"
         v-if="message.modifiedComments!==''">买家评价：{{ message.modifiedComments }}</p>
      <p style="margin-top: 1.40625vw"
         v-if="message.stars !== 0">买家评分：
      </p>
      <el-rate  v-if="message.stars !== 0" :disabled="true" v-model="message.stars" :colors="colors"
      style="position: absolute;top:89.5%;left: 40%">
      </el-rate>

    </div>

    <button class="small-btn" v-show="activeNumber===0&&person_account===message.customerAccount"
            style="position: absolute;top:100%;left:18%"
            @click="paidItNow"
    >去支付
    </button>
    <!--支付方式-->
    <div v-show="PaidWay">
      <div class="square" style=" position: absolute; top: 110%;left:10%"><p>支付方式</p></div>
      <button @click="openWX"
              class="image-btn" style="position: absolute; top:120%;left:10%">
        <img src="../../assets/Userimg/wechat.png" alt="Button Image">
        微信支付
      </button>
      <button @click="openZFB"
              class="image-btn" style="position: absolute; top: 120%;left:30%">
        <img src="../../assets/Userimg/alipay.png" alt="Button Image"
             class="picture">
        支付宝支付
      </button>
    </div>
    <!--微信支付-->
    <el-dialog :visible.sync="WXVisible" title="微信支付中">
      <img src="../../assets/Userimg/WXpaid.png" :style="{ width: '300px', height: 'auto'}">
      <span slot="footer" class="dialog-footer">
        <el-button @click="WXVisible = false">取消支付</el-button>
        <el-button type="primary" @click="WXConfirm">已完成支付</el-button>
      </span>
    </el-dialog>
    <!--    支付宝支付-->
    <el-dialog :visible.sync="ZFBVisible" title="支付宝 支付中">
      <img src="../../assets/Userimg/WXpaid.png" :style="{ width: '300px', height: 'auto'}">
      <span slot="footer" class="dialog-footer">
        <el-button @click="ZFBVisible = false">取消支付</el-button>
        <el-button type="primary" @click="ZFBConfirm">已完成支付</el-button>
      </span>
    </el-dialog>

    <!--    卖家关闭订单-->
    <button class="small-btn" v-show="activeNumber<1&&activeNumber>-1&&person_account===message.sellerAccount"
            style="position: absolute;top:100%;left:30%"
            @click="solderCloseOrder=true">关闭订单
    </button>
    <el-dialog :visible.sync="solderCloseOrder" title="卖家关闭订单确认">
      <p>是否确认要关闭订单？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="solderCloseOrder = false">取消</el-button>
        <el-button type="primary" @click="closeOrder1">确认</el-button>
      </span>
    </el-dialog>

    <!--    买家关闭订单-->
    <button class="small-btn" v-if="activeNumber<1&&activeNumber>-1&&person_account===message.customerAccount"
            style="position: absolute;top:100%;left:30%"
            @click="buyerCloseOrder=true">关闭订单
    </button>
    <el-dialog :visible.sync="buyerCloseOrder" title="买家关闭订单确认">
      <p>是否确认要关闭订单？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="buyerCloseOrder = false">取消</el-button>
        <el-button type="primary" @click="closeOrder2">确认</el-button>
      </span>
    </el-dialog>

    <button class="small-btn" v-show="activeNumber===1&&person_account===message.sellerAccount"
            style="position: absolute;top:100%;left:44%"
            @click="push_goods=true">去出库
    </button>
    <el-dialog :visible.sync="push_goods" title="出库中">
      <p>是否确认已经完成出库？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="push_goods = false">取消出库</el-button>
        <el-button type="primary" @click="pushSuccess">已完成出库</el-button>
      </span>
    </el-dialog>

    <button class="small-btn" v-show="activeNumber===2&&person_account===message.customerAccount"
            style="position: absolute;top:100%;left:43%"
            @click="confirmGood">确认收货
    </button>

    <button class="small-btn" v-if="activeNumber===3&&person_account===message.customerAccount&&message.modifiedComments===''"
            style="position: absolute;top:100%;left:57%"
            @click="openCommentDialog">去评价
    </button>

    <p style="color:#21675e;position: absolute;top:75%;left:15%;font-size: 1.5vw">订单状态</p>
    <p style="color: #c6ddc4; position: absolute;top:78%;left:13.75%;font-size: 1.5vw">Order State</p>

    <el-steps :space="200" v-show="activeNumber>-1" :active="activeNumber + 1" finish-status="success"
              style="width: 60%;position: absolute;top:90%;margin-left: 15%;">
      <!--      状态步骤条-->
      <el-step title="待支付"></el-step>
      <el-step title="已支付"></el-step>
      <el-step title="出库成功"></el-step>
      <el-step title="交易成功"></el-step>
    </el-steps>

    <el-steps :space="200" :active="activeNumber" finish-status="success"
              style="width: 60%;position: absolute;top:90%;margin-left: 15%;">
      <el-step title="管理员关闭" v-show="activeNumber===-1" status="error"></el-step>
      <el-step title="商家关闭" v-show="activeNumber===-2" status="error"></el-step>
      <el-step title="买家关闭" v-show="activeNumber===-3" status="error"></el-step>
    </el-steps>

    <!--    评价-->
    <el-dialog :visible="CommentDialogVisible" title="评价" @close="closeCommentDialog">
      <el-form ref="CommentForm" :model="CommentForm" label-width="120px">
        <el-form-item label="请输入您的评价">
          <el-input v-model="CommentForm.comment" name="comment"></el-input>
        </el-form-item>
      </el-form>

      <div class="block">
        <el-form>
          <el-form-item label="给这次购物打个分吧">
            <div style="position: absolute;left:20%;top: 30%">
              <el-rate @change="handleRatingChange"
                       v-model="value2"
                       :colors="colors"
              >
              </el-rate>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="CommentDialogVisible = false; value2 = 0">取消</el-button>
        <el-button type="primary" @click="saveComment()">确认</el-button>
      </div>
    </el-dialog>
    <p style="text-align: center;margin-top: 5vw;margin-bottom: 100vw;
      position: absolute;top:140%;
      opacity: 0"
    >占位符</p>
  </div>
</template>
<script>


export default {
  name: 'OrderNei',
  data() {
    return {
      person_account: "小花花花花",

      buyerCloseOrder: false,
      //买家关闭订单弹窗是否显示
      solderCloseOrder: false,
      //卖家关闭订单弹窗是否显示

      push_goods: false,

      // value1: null,
      value2: 0,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      //评分相关
      PaidWay: false,
      WXVisible: false,
      ZFBVisible: false,
      CommentDialogVisible: false,
      CommentForm: {
        comment: ''
      },
      activeNumber: 0,
      message:{
        orderNo:'',
        goodName:'',
        goodImg:'',
        customerAccount:'',
        sellerAccount:'',
        totalPrice:'',
        shoppingPhone:'',
        shippingAddress:'',
        initialComments:'',
        modifiedComments:'',
        stars:0,
        orderStatus:'',
      },
      backUrl: "http://localhost:8090/images/upload/",
    }
  },
  methods: {
    // 确认收货
    confirmGood(){
      this.$axios({
        url:this.$http + '/order/updateStatus',
        method:'post',
        data:{
          orderId:this.$route.query.orderId,
          orderStatus:3,
          payStatus:1
        },
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.activeNumber = 3
          this.$message.success("收货成功")
        }else{
          alert('更新数据失败')
        }
      });
    },
    closeOrder1(){
      //商家关闭订单
      this.$axios({
        url:this.$http + '/order/updateStatus',
        method:'post',
        data:{
          orderId:this.$route.query.orderId,
          orderStatus:-2,
          payStatus:1
        },
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.activeNumber = -2
        }else{
          alert('更新数据失败')
        }
      });
      this.solderCloseOrder = false;
    },

    closeOrder2() {
      //买家关闭订单
      this.$axios({
        url:this.$http + '/order/updateStatus',
        method:'post',
        data:{
          orderId:this.$route.query.orderId,
          orderStatus:-3,
          payStatus:1
        },
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.activeNumber = -3
        }else{
          alert('更新数据失败')
        }
      });
      this.buyerCloseOrder = false;
    },

    //加载订单消息
    loadOrderInfo(orderId) {
      this.$axios({
        url:this.$http + '/order/getOrderInfo?orderId=' + orderId,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.message = res.data
          this.activeNumber = this.message.orderStatus
        }else{
          alert('获取数据失败')
        }
      })
    },

    pushSuccess() {
      //后端操作
      this.$axios({
        url:this.$http + '/order/updateStatus',
        method:'post',
        data:{
          orderId:this.$route.query.orderId,
          orderStatus:2,
          payStatus:1
        },
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.activeNumber += 1;
          this.$message.success("出库成功")
          this.push_goods = false;
        }else{
          alert('更新数据失败')
        }
      });

    },

    handleRatingChange(){
      // 获取评分
      // 在这里可以处理评分值的变化
      this.$message.success('你的评分是' + this.value2)
    },

    openZFB()
    //打开支付宝支付
    {
      this.ZFBVisible = true;
    },
    ZFBConfirm() {
      this.ZFBVisible = false;
      //后端更新操作
      this.update(1)
      this.$router.go(0);
      this.$message.success('支付成功');
      this.PaidWay = false;
    },

    openWX()
    //打开微信支付
    {
      this.WXVisible = true;
    },
    WXConfirm() {
      this.WXVisible = false;
      //后端更新操作
      this.update(2)
      this.$router.go(0);
      this.$message.success('支付成功');
      this.PaidWay = false;
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

    paidItNow() {
      this.PaidWay = true;
    },


    // 评价
    saveComment() {
      if (this.CommentForm.comment !== "" && this.value2 > 0) {
        // this.message.modifiedComments = this.CommentForm.comment;
        //后端操作
        this.$axios({
          url:this.$http + '/comment/addComment',
          method:'post',
          data:{
            reviewerAccount:this.message.customerAccount,
            reviewedAccount:this.message.sellerAccount,
            initialComments:this.CommentForm.comment,
            stars:this.value2
          },
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){
            this.CommentDialogVisible = false; // 关闭编辑对话框
            this.loadOrderInfo(this.$route.query.orderId)
            this.$message.success('评价成功');
          }else{
            alert('评论失败')
          }
        });

      } else if (this.CommentForm.comment !== "") {
        this.$message.error('评价不能为空')
      } else {
        this.$message.error('请为这次购物评分！')
      }
    },

    resetForm(){
      this.CommentForm.comment = ''
    },

    openCommentDialog() {
      this.CommentDialogVisible = true;
      this.resetForm()//重置数据
    },
    closeCommentDialog() {
      if (this.CommentForm.comment !== "") {
        //后端操作

        this.CommentDialogVisible = false
        this.resetForm()//重置数据
      } else {
        this.$message.error('评价不能为空')
      }
    },
    //
  },

  mounted() {
    const orderId = this.$route.query.orderId;
    this.loadOrderInfo(orderId);
    this.person_account = JSON.parse(localStorage.getItem("person")).account
  },
}
</script>

<style scoped>

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


.small-btn {
  border-radius: 10px;
  background-color: #2D8578;
  color: white;
  padding: 8px 15px;
  font-size: 12px;
  cursor: pointer;
}

.small-btn:hover {
  background-color: #2b5b55;
}

.square p {
  color: white;
  font-size: 16px;
  font-weight: bold;
  margin: 0;
}

a {
  text-decoration: none;
}

.btn {
  font-size: 18px;
  color: white;
  border-radius: 150px;
  height: 40px;
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
  border-radius: 15px;
  background-color: #000;
  border: inherit;
  transform: translate(-50%, -50%);
  opacity: 0;
  content: ' ';
}

.btn:active::before {
  opacity: 0.1;
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

.image-btn:hover {
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