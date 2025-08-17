<template>
  <div :class="['global-background']">
    <div style="float:left;">
      <img height="240"
           src="../../assets/Userimg/logo.jpg"
           style="margin-left:21%;margin-top:15%;"
           width= "240">
      <h4 style="margin-top:1%;margin-left:20%;color:#21675e">线上校园二手物品交易平台</h4>
    </div>
    <div><img height="28"
              src="../../assets/Userimg/search.jpg"
              style="float:left;margin-left:8%;margin-top:5.75%;"
              width= "28"
    ></div>
    <div style="float:left;margin-left:1%;margin-top:5%;">
      <form @submit.prevent="search">
        <input class="search" placeholder="请输入想搜索的内容" style="height: 2.5vw;color: #2b5b55" size="70%" type="text" v-model="searchKeyword">
        <button class="btn" style="margin-left: 1.5vw">搜索</button>
      </form>
    </div>

    <div style="position: absolute;width:25%;height:16%; border:2px solid #21675e;top:30%;left:38%">
      <div v-for="search in searches" :key="search.categoryId" style="display: inline-block;width: 45%">
        <button     class="search-btn"
                    style="background-color:transparent;
                    width:80%;height: 60%;
                    color: #21675e;
                    border:0;" @click="searchByCategory(search.categoryId)">{{search.categoryName}}
        </button>
      </div>
    </div>

    <!--轮播图-->
    <template>
      <el-carousel indicator-position="outside" style="height:450px;width:800px;position: absolute;top:55%;left: 22%">
        <el-carousel-item  v-for="item in imgList" :key="item.id">
          <a :href="item.redirectUrl">
            <img :src="backUrl + item.carouselUrl" class="carousel-image">
          </a>
        </el-carousel-item>
      </el-carousel>
    </template>

    <!--  附近热卖  -->

    <p style="color:#d9e8d8; position: absolute;top:103%;left:15%;font-size: 1vw">——————————————————————————</p>
    <p style="color:#d9e8d8; position: absolute;top:103%;left:57%;font-size: 1vw">——————————————————————————</p>

    <p style="color:#21675e;position: absolute;top:100%;left:46%;font-size: 2vw">附近热卖</p>
    <p style="color: #c6ddc4; position: absolute;top:107%;left:43%;font-size: 1.5vw">HOT SELLING NEARBY</p>

    <div class="grid-container" style="position: absolute;top:120%;left:8%">
      <div v-for="(product, index) in products2" :key="index" class="grid-item">
        <!-- 商品内容 -->
        <img :src="backUrl+product.imgUrl" alt="Product Image" class="product-image" :style="{ backgroundImage: 'url(' + backUrl + product.image + ')' }">
        <div class="product-details">
          <h3>{{ product.goodName }}</h3>
          <p style="color: #606266" >价格：{{ product.price }}</p>
        </div>
        <el-button type="text" class="button" @click="viewDetails(product)">查看详情</el-button>
      </div>
    </div>

    <!--  猜你喜欢  -->

    <p style="color:#d9e8d8; position: absolute;top:235%;left:15%;font-size: 1vw">——————————————————————————</p>
    <p style="color:#d9e8d8; position: absolute;top:235%;left:57%;font-size: 1vw">——————————————————————————</p>

    <p style="color:#21675e;position: absolute;top:232%;left:46%;font-size: 2vw">猜你喜欢</p>
    <p style="color: #c6ddc4; position: absolute;top:239%;left:45%;font-size: 1.5vw">GUESS YOU LIKE</p>
    <div class="grid-container" style="position: absolute;top:252%;left:8%">
      <div v-for="(product, index) in products1" :key="index" class="grid-item">
        <!-- 商品内容 -->
        <img :src="backUrl+product.imgUrl" alt="Product Image" class="product-image" :style="{ backgroundImage: 'url(' + backUrl+ product.image + ')' }">
        <div class="product-details">
          <h3>{{ product.goodName }}</h3>
          <p style="color: #606266" >价格：{{ product.price }}</p>
        </div>
        <el-button type="text" class="button" @click="viewDetails(product)">查看详情</el-button>
      </div>
    </div>


    <p style="color: #606266;
      position: absolute;top:350%;left:45%;margin-top: 10vw"
    >更多商品请搜素</p>

    <p style="text-align: center;margin-top: 5vw;margin-bottom: 100vw;
      position: absolute;top:370%;
      opacity: 0"
    >占位符</p>



    <div>
      <p style="color:transparent;position: absolute;top:200%;left:2%;">000000</p>
    </div>
    <div style="float:left;border:1px solid #21675e;width: 100%;height:1%;margin-bottom: 0"></div>

  </div>
</template>

<script>

export default {
  name: "HomeOne",
  data() {
    return{
      searches:[

      ],
      cards: ['Card 1', 'Card 2', 'Card 3', 'Card 4', 'Card 5', 'Card 6', 'Card 7', 'Card 8', 'Card 9'],
      rows: [],
      currentDate: new Date(),
      imgList:[
      ], //轮播图
      //猜你喜欢
      products1:[
        // {
        //   "goodId": 16,
        //   "goodName": "Kiwi elite",
        //   "imgUrl": "47c28778-88a4-42fd-bb4d-c93fe8df36b5.jpg",
        //   "price": null
        // },
      ],
      //附近热卖
      products2:[
      //   {
      //     "imgUrl": "49c9f6f8-11c2-4f57-98b9-daf12715b938.jpg",
      //     "goodName": "Rambuton elite",
      //     "price": null,
      //     "goodId": 1
      //   },
      ],
      backUrl:"http://localhost:8090/images/upload/",

      searchKeyword: '',//搜索关键字
    }
  },
  methods: {
    //加载轮播图
    loadImgList(){
      this.$axios({
        url:this.$http + '/carousel/list',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.imgList = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },

    // 获取附近热卖商品
    loadProducts2(){
      this.$axios({
        url:this.$http + '/near-sell/getConfiguredById?campusId='+JSON.parse(localStorage.getItem("person")).campusId,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.products2 = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },

    //获取分类
    loadSearches(){
      this.$axios({
        url:this.$http + '/good-category/clist',
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.searches = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },

    // 获取猜你喜欢商品
    loadProducts1(){
      this.$axios({
        url:this.$http + '/good/getByCategoryIds?personId='+JSON.parse(localStorage.getItem("person")).personId,
        method:'get',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.products1 = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },

    chunkArray(arr, size) {
      const chunkedArray = [];
      for (let i = 0; i < arr.length; i += size) {
        chunkedArray.push(arr.slice(i, i + size));
      }
      return chunkedArray;
    },

    search() {
      // 执行搜索操作
      if (this.searchKeyword.trim() !== '') {
        this.$router.push({
          path: '/user/HomeTwo',
          query: { keyword: this.searchKeyword }
        });
      }else{
        this.$message.warning('请输入您要查找的关键词')
      }
    },

    searchByCategory(categoryId){
      // 执行基于分类的查询操作
      this.$router.push({
        path: '/user/HomeTwo',
        query: { keyword: categoryId, flag: true}
      });
    },

    viewDetails(product) {
      //记录用户浏览信息
      this.$axios({
        url:this.$http + '/views/addView',
        method:'post',
        data:{
          goodId:product.goodId,
          personId:JSON.parse(localStorage.getItem("person")).personId
        },
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode!==200){
          alert('数据传输失败')
        }
      })
      // 打开商品详情页，并传递product信息
      // 可以使用路由进行导航，并将product信息作为参数传递给商品详情页
      this.$router.push({
        path: '/user/GoodInformation',
        query: {productId: product.goodId}
      });
    },

  },
  mounted() {
    this.rows = this.chunkArray(this.cards, 3);
    this.loadImgList()
    this.loadProducts2()
    this.loadSearches()
    this.loadProducts1()
  },
  computed: {
    imgStyle() {
      return {
        transform: `translate3d(${this.imgDistance}px, 0, 0)` // 计算移动的距离(x,y,z)
      }
    }
  }
}

</script>

<style scoped>

.card {
  height:25.833vw;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  text-align: center;
  line-height: 2.042vw;
  margin-top: 10px;
  margin-left: 100px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 100px;
  margin: 0;
}

.el-card {
  min-width: 3px;
  min-height: 36px;
  margin-right: 20px;
  transition: all .5s;
}
.el-card:hover{
  margin-top: -2.604vw;
}
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  height: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

a{
  text-decoration: none;
}
.search-btn
{
  font-size: 14px;
  cursor: pointer;
}
.search-btn::before {
  position: absolute;
  width: 20%;
  height:50%;
  margin-left: 5%;
  margin-top: 2%;
  border-radius:15px;
  background-color: #000;
  border: inherit;
  transform: translate(-50%, -50%);
  opacity: 0;
  content: ' ';
}
.search-btn:active::before {
  opacity: 0.1;
}
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

.image{
  float: left;
  margin-top:0;
  margin-left: -70%;
  display: flex;
  left: 0;
  width: 600px;
  height: 300px;
  overflow: hidden;
  list-style: none;
}
.Img_li{
  float:left;
  margin: 0 8px;
  cursor: pointer;
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


.imgBox{
  border-top: 2px solid cadetblue;
  width: 110%;
  height: 230px;
  margin: 0 auto;

}

.zoom-image {
  transition: transform 0.2s ease-in-out;
}

.zoom-image:hover {
  transform: scale(1.2);
}


.imgBox img{
  width: 30%;
  height: 150%;
  margin-left: 30%;
  margin-top: 20%;
  padding-top: 30px;

}

.grid-container {
  display: grid;
  border-radius: 10px;
  grid-template-columns: repeat(4, 1fr);
  gap: 5vw;
}

.grid-item {
  border: 1px solid #ccc;
  padding: 1vw;
  text-align: left;
  transition: transform 0.5s ease-in-out; /* 调整过渡的持续时间和缓动函数 */
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3); /* 添加阴影效果 */
}

.grid-item:hover {
  transform: translateY(-20px); /* 鼠标滑过时向上移动5个像素 */
}
.product-details {
  margin-top: 10px;
}

.product-image {
  width: 15vw; /* 设置图片宽度为200像素 */
  height: 15vw; /* 设置图片高度为200像素 */
  background-size: cover;
  object-fit: cover; /* 根据需要调整图片对象的显示方式 */
}

.global-background {
  background-color: #e1e1db;
}

.carousel-image-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel-image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-image {
  width: 100%;

  height: 100%;
  object-fit: cover;
}

</style>