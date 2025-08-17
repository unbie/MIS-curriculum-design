<template>
    <div>
      <el-button type="success"  @click="goback()">返回</el-button>
      <el-button  type="success" @click="SubmitOrder()">提交订单</el-button>
      <el-button  type="danger"  @click="deleteGoods()">删除商品</el-button>

      <el-table :data="shoppingCart" :border="true" ref="messageTable" style="width: 90%; margin-top: 10%; margin-left: 5%; height: 20%">
        <el-table-column type="selection"></el-table-column>
        <el-table-column prop="good.imgUrl" label="图片">
          <template slot-scope="scope">
            <img :src="backUrl + scope.row.good.imgUrl" class="item-image" style="width: 128px;height: 128px"/>
          </template>
        </el-table-column>
        <el-table-column prop="good.good.goodName" label="名称"></el-table-column>
        <el-table-column prop="good.good.description" label="简介"></el-table-column>
        <el-table-column prop="good.good.price" label="价格/元"></el-table-column>
        <el-table-column prop="good.good.expressPrice" label="邮费"></el-table-column>
        <el-table-column prop="count" label="数量">
          <template slot-scope="scope">
            <div class="box">
              <div class="top">
                <el-tooltip class="item" effect="dark" :content="'数量范围为[1,' + scope.row.good.good.counts + ']'" placement="top">
                  <el-input-number v-model="scope.row.count" :min="1" :max="scope.row.good.good.counts"></el-input-number>
                </el-tooltip>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="count" label="总金额/元">
          <template slot-scope="scope">
            {{ scope.row.good.good.price * scope.row.count +scope.row.good.good.expressPrice}}
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="确认以下订单信息" :visible.sync="dialogTableVisible">
        <el-row>
          <el-col :span="12">
            <el-descriptions class=""  title="商品信息" :column="5" border>
              <el-descriptions-item label="商品名" v-if="editForm.good">{{ this.editForm.good.good.goodName}}</el-descriptions-item>
              <el-descriptions-item label="数量" v-if="editForm.good">{{  this.editForm.count}}</el-descriptions-item>
              <el-descriptions-item label="单价/元" v-if="editForm.good">{{ this.editForm.good.good.price }}</el-descriptions-item>
              <el-descriptions-item label="邮费" v-if="editForm.good">{{ this.editForm.good.good.expressPrice }}</el-descriptions-item>

              <el-descriptions-item label="总价/元" v-if="editForm.good">{{ this.editForm.count*this.editForm.good.good.price+this.editForm.good.good.expressPrice}}</el-descriptions-item>
            </el-descriptions>
          </el-col>
        </el-row>
        <br><br>
        <el-form ref="editForm" :model="editForm" :disabled="isLocked">
          <el-form-item label="收货地址">
            <el-select v-model="order.addressId" placeholder="请输入收货地址" style="width: 50%">
              <el-option v-for="(item2,index) in address" :key="index" :value="item2.shoppingAddressId" :label="item2.shoppingName+' '+item2.shoppingPhone+' '+item2.shippingAddress">
<!--                {{ item2.shippingAddress }} {{ item2.shoppingPhone }} {{ item2.shoppingName }}-->
              </el-option>
            </el-select>
          </el-form-item>
          <p>*注:如果选项里没有你想要的选项，请前往消息修改-地址修改添加新地址</p>
        </el-form>
        <div slot="footer" class="dialog-footer">
<!--          先点击确定 然后出来支付或者稍后支付-->
          <el-button @click="dialogTableVisible = false" v-show="confirm">取消</el-button>
          <el-button type="primary" v-show="confirm" @click="generateOrder()">确定</el-button>
            <el-button type="primary" v-show="paid_now" @click="toPay()">支付</el-button>
        </div>
      </el-dialog>
    </div>
</template>


  <script>

export default {
  name: "ShoppingCart",


  data () {
    return {
      backUrl: "http://localhost:8090/images/upload/", //回显的路径
      editForm:{

      },
      paid:true,
      paid_now:false,//是否立即支付
      confirm:true,
      deleted:false,
      dialogTableVisible: false,
      deleteVisible:false,
      isLocked:false,
      selectNumber:0,
      confirmData: [],

      //订单
      order:{
        orderId:'',
        orderNo:'',
        goodId:'',
        addressId:'',
        sellerId:'',
        customerId:'',
        totalPrice:'',
        orderStatus:'',
        payTime:'',
        payWays:'',
        payStatus:'',
        count:1,
        createTime:''

      },

      goodAndImg:{
        good:'',
        imgUrl:'',
      },
        orderSummary:'',

      //获取当前用户的地址
      address:[],
      //获取当前用户的购物车
      shoppingCart:[],
      //选中的数据
      selectedGoods:[],
      //获取当前最大的订单id
      MaxOrderId:'',
      //当前用户
      currentUser:'',
      currentUserId:'',


    };
  },
  methods:{
    toPay(){
      this.$router.push({
        path:'/user/SubmitOrder',
        query:{orderId:this.order.orderId}
      })
    },
    generateOrder(){

      if(this.order.addressId===""){
        this.$message.info("请选择收货地址")
        return;
      }

      this.confirm = false;//表示已经生成订单
      this.paid_now=true;
      this.isLocked=true;//不允许用户修改



      const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
      this.order.orderId=this.MaxOrderId+1;
      this.order.createTime = formattedDate;
      this.order.goodId = this.editForm.goodId;
      this.order.count = this.editForm.count;
      this.order.customerId = this.currentUserId;
      this.order.sellerId = this.editForm.good.good.publisherId;
      this.order.orderId=this.MaxOrderId+1;
      this.order.totalPrice = this.editForm.count*this.editForm.good.good.price+this.editForm.good.good.expressPrice
      this.order.payWays = 0
      this.order.payStatus = 0
      this.order.orderStatus = 0



      let data = this.order;
      this.delete(this.editForm.cartId)
      this.editGood(this.editForm.goodId,this.editForm.count)
      this.$axios({
        url:this.$http + '/order/create',
        data:data,
        method:'post',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
          alert('生成订单成功')

        }else{
          alert('生成订单失败')
        }
      })




    },
    deleteGoods() {
      // 删除轮播图
      const selectedRows = this.$refs.messageTable.selection; // 获取选中的行数据
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的商品');
        return;
      }
      this.$confirm('此操作将从购物车中删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 选中多条记录删除
        let cartIds = []
        for(let i =0; i < selectedRows.length; i++){
          cartIds.push(selectedRows[i].cartId)
        }
        //后端
        this.$axios({
          url:this.$http + '/shoppingcart/deleteGoods?cartIds=' + cartIds,
          method:'get',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            // this.loadCarouselList()
            this.getShoppingCart();
          }else{
            this.$message.error("删除失败")
          }
        })


      }).catch(()=>{
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })
    },
    delete(cartId){
      this.$axios({
        url:this.$http + '/shoppingcart/delete?cartId='+cartId,
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
        }else{
          alert('获取数据失败')
        }
      });
    },

    //如果买完了商品，直接删除
    deleteGood(goodId) {
        //后端
        this.$axios({
          url:this.$http + '/good/deleteGoods?goodId=' + goodId,
          method:'get',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{
          if(res.resultCode===200){

          }else{
          }
        })

      },


    //提交订单后，将商品数量减少
    editGood(goodId,count) {
      //获取选中的行数据
      this.$axios({
        url:this.$http + '/good/buy?goodId='+goodId+'&count'+count,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
        }else{
          alert('更新数据失败')
        }
      })


    },

    getAddress(personId){
      this.$axios({
        url:this.$http + '/person-shopping-address/getAddressesById?personId='+personId,
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
          this.address=res.data

        }else{
          alert('获取数据失败')
        }
      });
      },
    getShoppingCart(){
      let _this = this
      this.$axios({
        url:this.$http + '/shoppingcart/list?personId='+this.currentUserId,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {
            _this.shoppingCart = res.data;



        }
        else{
          alert("加载购物车数据失败")
        }
      });
    },
    //获得当前订单的最大值
    getMaxOrderId(){
      let _this = this
      this.$axios({
        url:this.$http + '/order/getMaxId',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>
      {

        if(res.resultCode==200)
        {
          _this.MaxOrderId = res.data;
          if (_this.MaxOrderId===null)
            _this.MaxOrderId = 0;
        }
        else{
          alert("加载订单数据失败")
        }
      });


    },
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


          if (_this.MaxCartId===null)
            _this.MaxCartId = 0;
        }
        else{
          alert("加载购物车数据失败")
        }
      });


    },

    SubmitOrder()
    //    提交订单按钮
    {
      const selectedRows = this.$refs.messageTable.selection;

      if (selectedRows.length === 0) {
        this.$message.warning('请选择要购买的商品');
        return;
      }else if(selectedRows.length !== 1){
        this.$message.warning("抱歉,您只能选择一条数据")
        return;
      }
      this.editForm = { ...selectedRows[0] };
      this.dialogTableVisible = true;
    },
    deleteConfirm(){
      if(this.selectedRows.length===0){
        this.$message.error('请选择至少一件商品')
      }
      else{
        this.deleteVisible=true;
      }
    },
    deleteSelected() {
      const selectedItems = this.items.filter(item => item.selected);
      selectedItems.forEach(item => {
        const index = this.items.indexOf(item);
        this.items.splice(index, 1);
      });
      this.deleteVisible=false;
    },

    goback(){
        this.$router.go(-1); // 返回上一个页面
    },
    changeView1()
    //    管理
    {
      this.manage=false;
      this.unmanaged=true;
       this.paid=false;
       this.deleted=true;
    },
    changeView2()
    //    退出管理
    {
      this.manage=true;
      this.unmanaged=false;
      this.paid=true;
      this.deleted=false;
    },
  },
  computed: {
  },

  mounted() {

    this.getShoppingCart();
    this.getAddress(this.currentUser.personId)
    this.getMaxOrderId();
    this.getMaxCartId();

  },
  beforeMount() {
    this.currentUser = JSON.parse(localStorage.getItem("person"));
    this.currentUserId = this.currentUser.personId;
  }


}
</script>

<style scoped>
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


.btn {
  font-size: 1vw;
  color:white;
  border-radius:7.8125vw;
  height:2.083vw;
  padding: 0 1.823vw;
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
  border-radius:0.78125vw;
  background-color: #000;
  border: inherit;
  transform: translate(-50%, -50%);
  opacity: 0;
  content: ' ';
}
.btn:active::before {
  opacity: 0.1;
}

.last{  min-height: calc(100vh - 100px); /* 页面高度减去页脚高度 */
  position: relative;}
</style>