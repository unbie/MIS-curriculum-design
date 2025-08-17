<template>
  <div>
    <el-card>
      <div>
        <div slot="header">
          <h2>订单管理</h2>
        </div>
        <el-divider></el-divider>
        <div class="search-container">
          <el-button type="primary" style="margin-left: 5px;" icon="el-icon-search" @click="seeDetails">查看详情</el-button>
          <el-button type="success" icon="el-icon-circle-close" @click="closeOrder">手动关闭</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteRows">删除订单</el-button>
          <el-input
              v-model="searchKeyword"
              placeholder="请输入订单号"
              icon="el-icon-search"
              @input="search"
              style="margin-left: 5vh;"
          ></el-input>
        </div>
        <br/>

        <!--      商品查看详情界面-->
        <el-dialog title="订单详情" :visible.sync="seeDetailsDialog" width="50%">
          <template v-if="selectedOrder">
            <el-row>
              <el-col :span="12">
                <el-card class="details-card">
                  <h3>商品信息</h3>
                  <p>商品序号: {{ selectedOrder.goodId }}</p>
                  <p>商品名称: {{ getGoodName(selectedOrder.goodId) }}</p>
                  <img :src="this.$backUrl + getGoodImage(selectedOrder.goodId)" alt="商品图片" />
                </el-card>
              </el-col>
              <el-col :span="12">
                <el-card class="details-card" style="background-color: #f5f5f5;">
                  <h3>消费者信息</h3>
                  <p>用户序号: {{ selectedOrder.customerId }}</p>
                  <p>用户名: {{ getCustomerName(selectedOrder.customerId) }}</p>
                  <p>收货人姓名: {{ getShippingPerson(selectedOrder.customerId) }}</p>
                  <p>收货地址: {{ getShippingAddress(selectedOrder.customerId) }}</p>
                  <p>收货人电话号码: {{ getShippingPhone(selectedOrder.customerId) }}</p>
                </el-card>
              </el-col>
            </el-row>
          </template>
          <template v-else>
            <p>请选择一条订单数据</p>
          </template>
        </el-dialog>


      </div>
    </el-card>
    <div>
      <el-main>
        <el-table
            :data="pagedCarouselList"
            :border="true"
            height="500"
            ref="orderTable"
            @selection-change="handleSelectionChange">
          <el-table-column type="selection" v-model="selectedRows"></el-table-column>
          <el-table-column prop="orderNo" label="订单号">
          </el-table-column>
          <el-table-column prop="goodId" label="商品序号">
          </el-table-column>
          <el-table-column prop="sellerId" label="卖家序号">
          </el-table-column>
          <el-table-column prop="customerId" label="消费者序号">
          </el-table-column>
          <el-table-column prop="count" label="数量">
          </el-table-column>
          <el-table-column prop="totalPrice" label="总价">
          </el-table-column>
          <el-table-column prop="orderStatus" label="订单状态">
            <template slot-scope="scope">
              <span :style="getStatusColor(scope.row.orderStatus)">
                {{ getOrderStatusText(scope.row.orderStatus) }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="payTime" label="支付时间">
          </el-table-column>
          <el-table-column prop="payWays" label="支付方式">
            <template slot-scope="scope">
              <span :style="getStatusColor(scope.row.payWays)">
                {{getPayWaysIndex(scope.row.payWays)}}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="payStatus" label="支付状态">
            <template slot-scope="scope">
              <span :style="getStatusColor(scope.row.payStatus)">
                {{getPayStatusIndex(scope.row.payStatus)}}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间">
          </el-table-column>
        </el-table>

        <!--      page-sizes 可选择的每页项数 -->
        <!--       layout 布局包括项数选择、上一页、页码、下一页、跳转 -->
        <!--      @size-change 项数选择改变时的事件-->
        <!--      current-change 页码改变时的事件-->
        <el-pagination
            :current-page="currentPage"
            :page-sizes="[2, 5, 10, 250]"
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
  name: "orderController",
  data() {
    return {
      // tableData: [
      //   {orderId: 1,orderNo:10086, goodId: 7, customerId: 8, totalPrice:114514.99, orderStatus:1, payTime:'2023-6-25', payWays:2, payStatus:0,},
      // ],
      // //测试数据
      // goodsList:[{goodId:7, goodName:'华为手机',goodImg:require('../../../../../market/src/main/resources/static/images/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg')}],
      // shippingInformationList:[{customerId:8,customerName:'原神真好玩',shippingPerson:'听风',shippingAddress:'成华大道',shippingPhone:'10086'}],

      tableData:[
      //     {
      //   orderId:0,
      //   orderNo:0,
      //   goodId:0,
      //   addressId:0,
      //   sellerId:0,
      //   customerId:0,
      //   totalPrice:0.00,
      //   orderStatus:0,
      //   payTime:'',
      //   payWays:0,
      //   payStatus:0,
      //   count:0,
      //   createTime:'',
      // }
      ],
      goodsList:[{
        goodId:0,
        goodName:'',
        goodImg:'',
        description:'',
      }],
      shippingInformationList:[{
        customerId:0,
        customerName:'',
        shippingPerson:'',
        shippingAddress:'',
        shippingPhone:0,
      }],

      searchKeyword: '', // 搜索关键字
      filteredData: [], // 过滤后的数据
      selectedRows:[],//选中的数据
      seeDetailsDialog:false,//查看详情界面
      selectedOrder: null,//选中的订单

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
          return regex.test(item.orderNo);
        });
      });
      // 检查过滤后的数据长度
      if (this.filteredData.length === 0) {
        // 弹出提示框，提示未找到指定数据
        this.$message.warning('未找到指定数据');
      }
    },
    handleSelectionChange(selection) {
      this.selectedOrders = selection;
    },
    seeDetails(){
      // 获取选中的行数据
      const selectedRows = this.$refs.orderTable.selection;
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要查询的数据');
        return;
      }else if(selectedRows.length !== 1){
        this.$message.warning("抱歉,您只能选择一条数据")
        return;
      }else{
        //进行查看详情操作
        this.selectedOrder = this.selectedOrders[0];
        // 通过goodId找到对应的商品信息
        const selectedGood = this.goodsList.find(good => good.goodId === this.selectedOrder.goodId);
        this.selectedOrder.goodInfo = selectedGood;
        // 通过customerId找到对应的收货信息
        const selectedShippingInfo = this.shippingInformationList.find(info => info.customerId === this.selectedOrder.customerId);
        this.selectedOrder.shippingInfo = selectedShippingInfo;
        this.seeDetailsDialog = true;
      }
    },
    closeOrder(){
      //管理员只能手动关闭订单，即只能将orderStatus的值改变为-1
      //获取选中的行
      const selectedRows = this.$refs.orderTable.selection;
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要关闭的订单');
        return;
      }
      selectedRows.forEach(row => {
        switch (row.orderStatus){
          case -1:
            this.$message.warning(`订单 ${row.orderNo} 已经手动关闭`);
            break;
          case -2:
            this.$message.warning(`订单 ${row.orderNo} 商家已经关闭`);
            break;
          case -3:
            this.$message.warning(`订单 ${row.orderNo} 买家已经关闭`);
            break;
          case 3:
            this.$message.warning(`订单 ${row.orderNo} 已经交易成功`);
            break;
          default:
            row.orderStatus = -1;
            row.orderStatusText = this.getOrderStatusText(row.orderStatus);
            // 在这里调用后端接口更新订单状态，将订单的orderStatus更改为-1
            // 后端操作
            //后端操作
            this.$axios({
              url:this.$http + '/order/closeOrder',
              method:'post',
              data:row,
              headers:{
                token:localStorage.getItem("token")
              }
            }).then(res=>res.data).then(res=>{
              if(res.resultCode===200){
                this.$message({
                  message: '订单已成功关闭！',
                  type: 'success'
                });
                this.loadOrder()
              }else{
                this.$message({
                  message: '操作失败！',
                  type: 'error'
                });
              }
            })
            break;
        }
      });
    },
    deleteRows(){
      //获取选中的行
      const selectedRows = this.$refs.orderTable.selection;
      if (selectedRows.length === 0) {
        this.$message.warning('请选择要删除的数据');
        return;
      }
      this.$confirm('此操作将永久删除该订单数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 获取选中的行数据
        const selectedRows = this.$refs.orderTable.selection;
        this.tableData = this.tableData.filter(order => !selectedRows.includes(order));
        this.filteredData = this.filteredData.filter(order => !selectedRows.includes(order));
        //后端
        for (let i = 0; i < selectedRows.length; i++) {
          this.deleteList.push(selectedRows[i].orderId);
        }
        this.$axios({
          url:this.$http + '/order/delete?orderId=' + this.deleteList,
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
            this.loadOrder();
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
    getOrderStatusText(status) {
      switch (status) {
        case 0:
          //用户编辑
          return '待支付';
        case 1:
          //用户编辑
          return '已支付';
        case 2:
          //商家编辑
          return '出库成功';
        case 3:
          //用户编辑
          return '交易成功';
        case -1:
          //管理员编辑
          return '管理员关闭';
        case -2:
          //商家编辑
          return '商家关闭';
        case -3:
          //买家编辑
          return '买家关闭';
        default:
          return '';
      }
    },
    getPayWaysIndex(payWays){
      switch (payWays){
        case 0:
          return '无';
        case 1:
          return '支付宝支付';
        case 2:
          return '微信支付';
        default:
          return '';
      }
    },
    getPayStatusIndex(payStatus){
      //// console.log(payStatus)
      switch (payStatus){
        case -1:
          return '支付失败';
        case 0:
          return '未支付';
        case 1:
          return '支付成功';
        default:
          return '';
      }
    },
    getStatusColor(orderStatus) {
      let color = '';
      switch (orderStatus) {
        case 0:
          color = 'blue';
          break;
        case 1:
          color = 'purple';
          break;
        case 2:
          color = 'orange';
          break;
        case 3:
          color = 'green';
          break;
        case 4:
          color = 'teal';
          break;
        case -1:
          color = 'red';
          break;
        case -2:
          color = 'gray';
          break;
        case -3:
          color = 'pink';
          break;
        default:
          color = '';
          break;
      }
      return { color };
    },
    getGoodName(goodId) {
      const good = this.goodsList.find((item) => item.goodId === goodId);
      return good ? good.goodName : '';
    },
    getGoodImage(goodId) {
      const good = this.goodsList.find((item) => item.goodId === goodId);
      return good ? good.goodImg : '';
    },
    getCustomerName(customerId) {
      const customer = this.shippingInformationList.find(
          (item) => item.customerId === customerId
      );
      return customer ? customer.customerName : '';
    },
    getShippingPerson(customerId) {
      const customer = this.shippingInformationList.find(
          (item) => item.customerId === customerId
      );
      return customer ? customer.shippingPerson : '';
    },
    getShippingAddress(customerId) {
      const customer = this.shippingInformationList.find(
          (item) => item.customerId === customerId
      );
      return customer ? customer.shippingAddress : '';
    },
    getShippingPhone(customerId) {
      const customer = this.shippingInformationList.find(
          (item) => item.customerId === customerId
      );
      return customer ? customer.shippingPhone : '';
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
    loadOrder(){
      this.$axios({
        url:this.$http + '/order/listO',
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
    loadOrderGood(){
      this.$axios({
        url:this.$http + '/order/listOG',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
          this.goodsList=res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
    loadOrderCustomer(){
      this.$axios({
        url:this.$http + '/order/listOC',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode==200){
          this.shippingInformationList=res.data
        }else{
          alert('获取数据失败')
        }
      })
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
    this.loadOrder();
    this.loadOrderCustomer();
    this.loadOrderGood();
  }
}
</script>

<style scoped>
.search-container {
  display: flex;
  align-items: center;
  height: 40px;
}
.details-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.details-column {
  flex: 1;
  margin-right: 10px;
}
.details-card {
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
}
</style>