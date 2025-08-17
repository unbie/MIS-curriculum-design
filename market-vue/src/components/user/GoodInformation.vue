<template>
  <div>
    <div>
      <p style="color:transparent;position: absolute;top:250%;left:2%;">000000</p>
    </div>
    <div style="float:left">
      <button class="btn" @click="goback()"><i class="el-icon-back"> 返回</i></button>
    </div>

    <div style="float:right;">
      <p class="goodCollect"><i id="uncollected" v-show="uncollected"
                                class="el-icon-star-off"
                                style="font-size: 20px" @click="changeCollect()"
      >收藏 </i></p>
    </div>

    <div style="float:right;">
      <p class="goodCollect">
        <i id="collected" v-show="collected"
           class="el-icon-star-on"
           style="font-size: 20px"
           @click="changeunCollect()"
        >取消收藏</i></p>
    </div>

    <div class="user" style="position: absolute;top:15%;left:12%" @click="toPersonInfo">
      <!--        这是商家头像-->
      <img class="user-click"
           :src="backUrl+sellerPerson.avatarUrl"
           width="40" height="40"
      >
    </div>

    <div style="position: absolute;top:12%;left: 15%">
      <p style="font-size: 18px"> {{ sellerPerson.userName }}</p>
    </div>
    <!--    卖家昵称-->
    <p style="font-weight:bold;font-size: 25px;color: #21675e;
    position: absolute;top:15%;left:60%">
      商品名：{{ goodProperty.goodName }}
    </p>

    <div class="goodContent">
      <textarea name=""

                style="position: absolute;
      height: 18vw;width: 35vw; line-height: 4vw;
                font-size:1.6vw;
                resize: none;
                padding: 1vw 1vw;
                top:25%;left:50%;
                color: #21675e;"
                :placeholder="goodProperty.description" disabled>

      </textarea>
      <p style="font-weight:bold;font-size: 25px;color: #21675e;
    position: absolute;top:63%;left:55%">
        商品价格：{{ goodProperty.price }}元
      </p>
      <p style="font-weight:bold;font-size: 25px;color: #21675e;
    position: absolute;top:63%;left:75%">
        邮费：{{ goodProperty.expressPrice}}元
      </p>
    </div>

    <div style="position: absolute;top: 75%;left: 54%">
      <button class="btn" @click="addVisible=true" :disabled="this.currentUserId===this.sellerId">
        <i class="el-icon-shopping-cart-full"> 加入购物车</i></button>
    </div>

    <el-dialog title="加入购物车" :visible.sync="addVisible" >
      <p>请选择加入购物车的数量:
        <el-tooltip effect="dark" :content="'数量范围为[1,'+goodProperty.counts+']' " placement="top">
          <input type="number" v-model="addNum" min="1" :max="goodProperty.counts">
        </el-tooltip>
        <br>
        <el-button type="primary" @click="addGoods()"
                   style="float: right"
        >确 定
        </el-button>
        <el-button @click="addVisible = false"
                   style="float: right">取 消
        </el-button>
      </p>
      <br>
    </el-dialog>

    <div style="position: absolute;top: 75%;left: 74%">
      <button class="btn"
              @click="SubmitOrder" v-if="currentUser&&sellerId" :disabled="this.currentUserId===this.sellerId" >
        <i class="el-icon-s-claim"> 立即购买</i></button>
    </div>
    <!--这里是当前用户头像？-->
    <div class="user" style="position:absolute;top:110%;left:15%;">
      <img v-if="currentUser"
           class="user-click"
           :src="backUrl+currentUser.avatarUrl"
           width="40" height="40">
    </div>


    <div class="chatWith">
      <textarea v-model="text_comment" cols="80" rows="2"
                style="position: absolute;
                font-size:14px;
                resize: none;
                top:110%;left:20%;
                color: #21675e;"
      >
      </textarea>
    </div>
    <div style="position:absolute;top:100%;left:10%;">
      <i class="el-icon-edit-outline"
         style="background-color: #2D8578;font-size: 35px"
      > 留言</i>
    </div>


    <div style="position: absolute;top: 115%;margin-left: 20%;color: #21675e;width: 50%">
      <!--      留言区-->
      <div v-for="comment in leaveMessages" :key="comment.commentId" >
        <p style="float: left;
         ">{{ comment.userName }}</p>
        <p style="float: right">{{ comment.createTime }}</p>
        <p style="float: left;margin-left: 10%">{{ comment.content }}</p>
        <div v-for="answer in comment.children" :key="answer.commentId"
             style="clear: both;">
          <p style="float: left;margin-left: 20%;
          ">{{ answer.userName }}</p>
          <p style="float: right">{{ answer.createTime }}</p>
          <p style="float: left;margin-left: 10%;">{{ answer.content }}</p>
        </div>

        <p style="color:#d9e8d8;
        float: left;display: inline;font-size: 1vw;
         white-space: nowrap;"
        >———————————————————————————————————————————————<i class="el-icon-chat-dot-round"
                                                           style="font-size: 24px;color: #21675e;"
                                                           @click="AnswerIt(comment.commentId)"
        ></i></p>

      </div>

      <el-dialog :visible="AnswerDialogVisible" title="回复留言" @close="closeAnswerDialog">
        <el-form ref="AnswerForm" :model="AnswerForm" label-width="130px">
          <el-form-item label="请输入回复的内容">
            <el-input v-model="AnswerForm.answer" name="Answer"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="AnswerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveAnswer()">确认</el-button>
        </div>
      </el-dialog>
    </div>


    <div style="position:absolute;top:110%;left:65%;">
      <button class="btn" @click="wantComment" :disabled="this.currentUserId===this.sellerId">
        <i class="el-icon-edit"> 留言</i></button>
    </div>
    <p style="color:transparent;position: absolute;top:180%;left:2%;">000000</p>


    <div>
      <div style="width:36.458vw;height:41.667vw;margin:20px 0 0 700px;">
        <div style="width:500px;height:400px;
        position: absolute;
        top: 23%;
        left:8%;">
          <img v-if="mainImgUrl" :src="backUrl+mainImgUrl"
               style="
               width: 100%;height:100%">
        </div>
        <div style="position: absolute;top: 50%;left: 30%">
          <i style="font-size: 2vw;display: inline-block;position: absolute;
           left:-80%;top: 85%;cursor: pointer;" class="el-icon-arrow-left" @click="imgLeft()"></i>
          <ul class="Img_ul">
            <li v-for="(item,index) in imgUrlList" :src="backUrl+item" :key="index"
                class="Img_li" :style="imgStyle" @click="changeImg(item, index)">
              <img v-if="backUrl+item" :class="index === imgActiveIndex ? 'img_activeBorder' : ''" :src="backUrl+item"
                   style="width:50px;height:50px">
            </li>
          </ul>
          <i style="font-size: 2vw;display: inline-block;position: absolute;
          left: 45%;top: 85%;cursor: pointer;" class="el-icon-arrow-right" @click="imgRight()"></i>
        </div>
      </div>
    </div>

    <div>
      <div>
      </div>
      <el-dialog title="确认以下订单信息" :visible.sync="dialogTableVisible">
        <table>
          <thead>
          <th>商品名
          </th>
          <th>数量
          </th>
          <th>单价/元
          </th>
          <th>邮费
          </th>
          <th>总价/元
          </th>
          </thead>
          <tbody style="color:  #21675e;height: 4.167vw">
          <tr>
            <td style="width: 26.042vw;text-align: center;color: black">{{ goodProperty.goodName }}</td>
            <td style="text-align: center;color: black">
              <el-tooltip class="item" effect="dark" :content="'数量范围为[1,'+goodProperty.counts+']'" placement="top">
                <input type="number"
                       v-model="order.count" min="1" :max="goodProperty.counts"/>
              </el-tooltip>
            </td>
            <td style="text-align: center;color: black">{{ goodProperty.price }}</td>
            <td style="text-align: center;color: black">{{ goodProperty.expressPrice }}</td>
            <td style="text-align: center;color: black">{{ goodProperty.expressPrice+order.count * goodProperty.price }}</td>
          </tr>
          </tbody>
        </table>
        <el-form :model="order" :disabled="isLocked">
          <el-form-item label="收货地址">
            <el-select v-model="order.addressId" placeholder="请选择收货地址">
              <el-option v-for="item2 in address" :key="item2.shoppingAddressId" :value="item2.shoppingAddressId"
                         :label="item2.shoppingName+' '+ item2.shoppingPhone+' '+ item2.shippingAddress"
              >{{ item2.shoppingName }} {{ item2.shoppingPhone }} {{ item2.shippingAddress }}
              </el-option>
            </el-select>
          </el-form-item>
          <p>*注:如果选项里没有你想要的选项，请前往消息修改-地址修改添加新地址</p>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible = false" v-show="confirm">取 消</el-button>
          <el-button @click="changeView2" v-show="paid_now">稍后支付</el-button>
          <el-button type="primary" v-show="confirm" @click="changeView1()">确 定</el-button>
          <el-button type="primary" @click="toPay()" v-show="paid_now">支付</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>

export default {
  name: "GoodInformation",
  data() {
    return {
      //保存回复
      leaveMessages: [],//一级回复
      showedMessages: [],//二级回复
      allMessages: [],//二级回复
      MaxLeaId:0,

      address: [],
      //当前评论的id
      comment_id: '',

      //当前最大的订单id
      MaxOrderId:'',
      MaxCartId:'',

      //商品信息
      currentUserId: '',
      currentUser: '',//当前登录用户对象
      goodProperty: '',
      goodId: '',
      sellerId: '',//商家id
      sellerPerson: '',
      backUrl: "http://localhost:8090/images/upload/", //回显的路径
      addNum:1,//加入购物车的数量
      addVisible:false,//加入表单的可见性
      good_name:"麻辣香锅底料",
      good_price:88,
      good_num:1,
      goodNum:1,
      good_max:28,
      isLocked:false,
      confirm:true,
      paid_now:false,

      text_comment: "",
      stringKONG: "0000000000000000000000000000000",
      stringKONG2: "000000000000000000000000000",
      AnswerDialogVisible: false,
      AnswerForm: {
        answer: ''
      },


      dialogTableVisible: false,
      order: {
        orderId: '',
        orderNo: '',
        goodId: '',
        addressId: '',
        sellerId: '',
        customerId: '',
        totalPrice: '',
        orderStatus: '',
        payTime: '',
        payWays: '',
        payStatus: '',
        count: 1,
        createTime: ''

      },

      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px',
      collected: false,
      uncollected: true,
      mainImgUrl: '',
      imgUrlList: [],
      imgActiveIndex: 0, // 当前移动图片的索引值
      imgDistance: 0, // 移动的距离
      allDistance: 0, // 总移动距离


    }
  },
  computed: {
    imgStyle() {
      return {
        transform: `translate3d(${this.imgDistance}px, 0, 0)` // 计算移动的距离(x,y,z)
      }
    }
  },
  methods:
      {
        toPay() {
          this.$router.push({
            path: '/user/SubmitOrder',
            query: {orderId: this.order.orderId}
          })
        },

        toPersonInfo() {
          this.$router.push({
            path: '/user/personInfo',
            query: {personId: this.sellerId}
          });
        },
//获取购物车最大id
    getMaxCartId(){
      let _this = this
      this.$axios({
        url:this.$http + '/shoppingcart/getMaxId',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {


          _this.MaxCartId = res.data;


          if (_this.MaxCartId===-Infinity)
            _this.MaxCartId = 0;
        }
        else{
          alert("加载购物车数据失败")
        }
      });


    },

    getMaxLeaId(){
      let _this = this
      console.log("getMaxLeaId")
      this.$axios({
        url:this.$http + '/leave-messages/getMaxId',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {
          console.log("RESULT")
          //console.log(res.data)
          _this.MaxLeaId =res.data;
          console.log(_this.MaxLeaId)
          //_this.MaxleaId2 = _this.MaxLeaId
        }
        else{
          console.log("失败")
          alert("加载留言数据失败")
        }
      });


    },

    //获取当前数据库订单的最大id
    getMaxOrderId() {
      let _this = this
      this.$axios({
        url: this.$http + '/order/getMaxId',
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {

        if (res.resultCode == 200) {


          _this.MaxOrderId = res.data;

          if (_this.MaxOrderId === null)
            _this.MaxOrderId = 0;
        } else {
          alert("加载订单数据失败")
        }
      });


    },


    generateOrder() {
      const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
      this.order.createTime = formattedDate;
      this.order.orderNo = 0;
      this.order.goodId = this.goodId;
      this.order.customerId = this.currentUserId;
      this.order.sellerId = this.sellerId;
      this.order.orderStatus = 0;
      this.order.payStatus = 0;
      this.order.orderId = this.MaxOrderId + 1;
      this.order.totalPrice = this.order.count * this.goodProperty.price+this.goodProperty.expressPrice


      let data = this.order;
      this.$axios({
        url: this.$http + '/order/create',
        data: data,
        method: 'post',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.$message.success('订单生成成功，点击支付立即前往支付');
        } else {
          this.$message.error('订单生成失败！');
        }
      })
    },
    getAddress(personId) {
      this.$axios({
        url: this.$http + '/person-shopping-address/getAddressesById?personId=' + personId,
        method: "get",
        headers: {
          token: localStorage.getItem("token")
        },
      }).then(res => res.data).then(res => {
        if (res.resultCode == 200) {
          this.address = res.data
        } else {
          alert('获取数据失败')
        }
      });
    },
    getGoodData(goodId) {
      let _this = this
      this.$axios({
        url: this.$http + '/good/getAGood?goodId=' + goodId,
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {

        if (res.resultCode == 200) {
          _this.goodProperty = res.data;
          this.sellerId = _this.goodProperty.publisherId;
          this.currentUser = JSON.parse(localStorage.getItem("person"))
          this.currentUserId = this.currentUser.personId;
          if (_this.goodProperty.image === null)
              // eslint-disable-next-line no-empty
          {

          } else {
            this.imgUrlList = this.goodProperty.image;
            this.mainImgUrl = this.imgUrlList[0];
          }

          //加载用户数据

          this.loadPerson(this.sellerId);
          this.getAddress(this.currentUserId)


        } else {
          alert("失败")
        }
      });


    },
    loadPerson(personId) {
      this.$axios({
        url: this.$http + '/person/getAPerson?personId=' + personId,
        method: "get",
        headers: {
          token: localStorage.getItem("token")
        },
      }).then(res => res.data).then(res => {
        if (res.resultCode == 200) {
          this.sellerPerson = res.data
          // //console.log(this.sellerPerson)
          // //console.log("what about you")
        } else {
          alert('获取数据失败')
        }
      })
    },
    getLeaveMessages(goodId) {
      let _this = this
      this.$axios({
        url: this.$http + '/leave-messages/list?goodId=' + goodId,
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {

        if (res.resultCode == 200) {
          //console.log("从后端获取的数据")
          _this.showedMessages = res.data;
          //console.log(_this.showedMessages)
          _this.leaveMessages = _this.showedMessages.filter(lea => lea.parentId === null)
          //console.log(_this.leaveMessages)


        } else {
          //console.log("失败")
          alert("失败")
        }
      })

    },
    addGoods(){
      if(this.addNum<=0){
        this.$message.error('错误的数量，请重新选择')
      }
      else if(this.addNum>this.goodProperty.counts)
      {
        this.$message.error('数量超过当前商品的库存，请重新选择')
      }
      else {
        let newRow = null;
        this.addVisible = false;
        const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
        //给新数据赋值
        newRow={
          cartId:this.MaxCartId+1,
          createTime : formattedDate,
          customerId: this.currentUserId,
          goodId:this.goodId,
          count:this.addNum,

        }
        let data = newRow;
        this.$axios({
          url:this.$http + '/shoppingcart/add',
          data:data,
          method:'post',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode === 200){
            this.$message.success('添加成功，数量为' + this.addNum)
            this.getMaxCartId();
          }else{
            alert('更新数据失败')
          }
        })


      }
    },
    //回复评论
    AnswerIt(commentId) {
      this.comment_id = commentId;
      this.AnswerDialogVisible = true;
    },
    closeAnswerDialog() {
      this.AnswerDialogVisible = false
      this.AnswerForm.answer = "";//重置数据
      this.comment_id = '';
    },
    saveAnswer() {

      this.MaxLeaId += 1;
      if (this.AnswerForm.answer === "") {
        this.$message.error('请输入留言内容')
      } else {
        let newRow = null;
        const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
        console.log("MAXLEAID2")
        console.log(this.MaxLeaId)

        newRow = {
          commentId: Math.floor(Math.random() * 100000000),
          reviewerId: this.currentUser.personId,
          parentId: this.comment_id,
          goodId: this.goodId,
          userName: this.currentUser.userName,
          content: this.AnswerForm.answer,
          createTime: formattedDate,
          target: '',
          goodName: this.goodProperty.goodName,
        };
        console.log("newRow.commentId")
        console.log(newRow)
        let data = newRow;
        this.$axios({
          url: this.$http + '/leave-messages/saveOrModGood',
          data: data,
          method: 'post',
          headers: {
            token: localStorage.getItem("token")
          }
        }).then(res => res.data).then(res => {
          if (res.resultCode === 200) {
            this.getLeaveMessages(this.goodId);
          } else {
            alert('更新数据失败')
          }
        })
        this.$message.success('回复成功');
        this.AnswerDialogVisible = false;
      }

    },

    save(index) {
      let data = this.showedMessages[index];
      this.$axios({
        url: this.$http + '/leave-messages/saveOrModGood',
        data: data,
        method: 'post',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          //alert('更新数据成功')
          this.getLeaveMessages(this.goodId);
        } else {
          //this.getLeaveMessages(this.goodId);
          alert('更新数据失败')
        }
      })
    },
    wantComment() {
      console.log("现在的评论id")
      console.log(this.MaxLeaId)
      let newRow = null;

      if (this.text_comment === "") {

        this.$message.error('留言内容不能为空')

      } else {
        //发送新增留言给后端

        const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
        console.log("LEAVEMESSAGE")
        console.log(this.MaxLeaId)
        newRow = {
          commentId: Math.floor(Math.random() * 100000000),
          reviewerId: this.currentUser.personId,
          parentId: '',
          goodId: this.goodId,
          userName: this.currentUser.userName,
          content: this.text_comment,
          createTime: formattedDate,
          target: '',
          goodName: this.goodProperty.goodName,


        };
        console.log("rrrr")
        console.log(newRow.commentId)
        this.showedMessages.push(newRow);

        const index = this.showedMessages.length - 1
        this.save(index);

        console.log(this.MaxLeaId)
        //发送新增请求
        this.text_comment = "";//清空
        this.$message.success('留言成功')
      }
    },
    changeView1() {
      // 确认
      if (this.order.addressId !== '') {
        // Perform form submission or other actions
        this.confirm = false;
        this.paid_now = true;
        this.isLocked = true;
        //生成订单
        this.generateOrder();
        this.getMaxOrderId();
      } else {
        this.$message.error('请选择收货地址！');
      }
    },
    changeView2() {
      //  稍后支付
      this.dialogTableVisible = false;
      this.isLocked = false;
      this.confirm = true;
      this.paid_now = false;
      this.$message.info('未支付订单前往订单中心-查看详情进行支付')
    },
    SubmitOrder()
    //    提交订单按钮
    {
      if (this.calculateSelected === 0) {
        this.$message.error('请选择至少一件商品')
      } else {
        this.dialogTableVisible = true;
      }
    },
    goback() {
      this.$router.go(-1); // 返回上一个页面
    },
    changeCollect() {
      this.collected = true;
      this.uncollected = false;
    },
    changeunCollect() {
      this.collected = false;
      this.uncollected = true;
    },
    changeImg(item, index) {
      this.mainImgUrl = item
      this.imgActiveIndex = index
    },
    imgLeft() {
      if (this.imgActiveIndex > 0) {
        this.imgActiveIndex--  // 索引值-1
        this.imgUrlList.forEach((item, index) => { // 循环小图,通过判断索引值赋值给大图
          if (this.imgActiveIndex === index) {
            this.mainImgUrl = item
          }
        })
      }
      if (this.imgActiveIndex >= 4) {
        var index = 0
        const temp = window.setInterval(() => { // 利用定时器实现图片左右移动的动画效果
          if (index < 33) { // 移动次数(33次)
            this.imgDistance += 2 // 每次向左移动的距离 (移动总距离为33*this.imgDistance)
            index++
            return
          } else {
            window.clearInterval(temp) // 移动完清除定时器
          }
        }, 10)
      }
    },
    imgRight() {
      if (this.imgActiveIndex < this.imgUrlList.length - 1) {
        this.imgActiveIndex++
        this.imgUrlList.forEach((item, index) => {
          if (this.imgActiveIndex === index) {
            this.mainImgUrl = item
          }
        })
        if (this.imgActiveIndex >= 5) {
          this.allDistance = -66 * (this.imgActiveIndex - 4)
          var index = 0
          const temp = window.setInterval(() => {
            if (index < 33) {
              this.imgDistance -= 2 // 每次向右移动的距离
              index++
              return
            } else {
              window.clearInterval(temp)
            }
          }, 10)
        }
      } else if (this.imgActiveIndex === this.imgUrlList.length - 1) { // 到达最后一张图片，再点击跳转回第一张
        this.imgActiveIndex = 0;
        this.mainImgUrl = this.imgUrlList[0]
        const temp = window.setInterval(() => { // 利用定时器实现图片左右移动的动画效果
          if (index < Math.abs(this.allDistance / 2)) { // 取绝对值再除
            this.imgDistance += 2 // 每次向左移动的距离 (移动总距离为33*this.imgDistance)
            index++
            return
          } else {
            window.clearInterval(temp) // 移动完清除定时器
          }
        }, 1)
      }
    },
  },
  mounted() {


    this.goodId = this.$route.query.productId;
    this.getGoodData(this.goodId);
    this.getMaxOrderId();
    this.getLeaveMessages(this.goodId);
    this.getMaxCartId();
    this.getMaxLeaId();

  },
  created() {

  },
  beforeMount() {

    this.currentUser = JSON.parse(localStorage.getItem("person"));
  }
}


</script>

<style scoped>

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


.user {
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  width: 40px;
}

.footer {
  position: fixed;
  bottom: 0px;
  width: 100%;
  z-index: 999;
}


.Img_ul {
  float: left;
  margin-top: 70%;
  margin-left: -70%;
  display: flex;
  left: 36px;
  width: 322px;
  height: 50px;
  overflow: hidden;
  list-style: none;
}

.Img_li {
  float: left;
  margin: 0 8px;
  cursor: pointer;
}

.img_activeBorder {
  border: 3px solid #0dcc73;
}


</style>