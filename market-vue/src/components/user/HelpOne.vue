<template>
  <div>

    <div style="float:left;">
      <img height="240"
           src="../../assets/Userimg/logo.jpg"
           style="margin-left:21%;margin-top:15%;"
           width="240">
      <h4 style="margin-top:1%;margin-left:20%;color:#21675e">线上校园二手物品交易平台</h4>
    </div>
    <div><img height="24"
              src="../../assets/Userimg/search.jpg"
              style="float:left;margin-left:8%;margin-top:5.75%;"
              width="24"
    ></div>

    <div style="float:left;margin-left:1%;margin-top:5%;">
      <form @submit.prevent="search">
        <input class="search" placeholder="请输入想搜索的内容" style="height: 2.5vw;color: #2b5b55" size="70%" type="text" v-model="searchKeyword">
        <button class="btn" style="margin-left: 1.5vw">搜索</button>
      </form>
    </div>

    <div style="position: absolute;width:35%;height:6%; border:2px solid #21675e;top:30%;left:28%">
      <div v-for="search in searches" :key="search.categoryId" style="display: inline-block;width: 25%">
        <button     class="search-btn"
                    style="background-color:transparent;
                    width:80%;height: 50%;
                    color: #21675e;
                    border:0;" @click="searchByCategory(search.categoryId)">{{search.categoryName}}
        </button>
      </div>
    </div>


    <!--求购广场-->
    <div class="grid-container" style="position: absolute;top:70%;left:8%">
      <div v-for="(product, index) in paginatedProducts1" :key="index" class="grid-item">
        <!-- 商品内容 -->
        <img :src="backUrl + product.imgUrl" alt="Product Image" class="product-image"
             :style="{ backgroundImage: 'url(' + backUrl + product.image + ')' }">
        <div class="product-details">
          <h3>{{ product.goodName }}</h3>
          <p style="color: #606266">心理价：{{ product.price }}￥</p>
        </div>
        <el-button type="text" class="button" @click="viewDetails(product)">查看详情</el-button>
      </div>
    </div>

    <!-- 分页按钮 -->
    <div class="pagination" style="position: absolute;top:58%;left:10%">
      <el-button v-for="page in displayedPages" :key="page"
                 @click="changePage(page)" :class="{ active: page === currentPage }">{{ page }}
      </el-button>
    </div>


    <div>
      <p style="color:#21675e;position: absolute;top:200%;left:2%;">000000</p>
    </div>
    <div style="float:left;border:1px solid #21675e;width: 100%;height:1%;margin-bottom: 0"></div>

  </div>
</template>

<script>

export default {
  name: "HelpOne",
  data() {
    return {
      searches: [

      ],

      cards: ['Card 1', 'Card 2', 'Card 3', 'Card 4', 'Card 5', 'Card 6', 'Card 7', 'Card 8', 'Card 9'],
      rows: [],

      currentPage: 1,
      itemsPerPage: 8, // 每页显示的商品数量

      products1: [
        // {name: '商品1', price: 100, image: require('../../assets/img/upload/0b02244f-6908-4ccb-a9d2-ccb5a462e30e.jpg')},
      ],

      backUrl:"http://localhost:8090/images/upload/",

      searchKeyword:'',
    }
  },
  methods: {

    // 获取所有求购商品
    loadShopForGoods(){
      this.$axios({
        url:this.$http + '/good/loadShopForGoods',
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

    changePage(page) {
      this.currentPage = page;
    },

    chunkArray(arr, size) {
      const chunkedArray = [];
      for (let i = 0; i < arr.length; i += size) {
        chunkedArray.push(arr.slice(i, i + size));
      }
      return chunkedArray;
    },

    searchByCategory(categoryId){
      // 执行基于分类的查询操作
      this.$router.push({
        path: '/user/HelpTwo',
        query: { keyword: categoryId, flag: true}
      });
    },

    viewDetails(product) {
      // 打开商品详情页，并传递product信息
      // 可以使用路由进行导航，并将product信息作为参数传递给商品详情页
      this.$router.push({
        path: '/user/BuyInformation',
        query: {productId: product.goodId}
      });
    },

    search() {
      // 执行搜索操作
      if (this.searchKeyword.trim() !== '') {
        this.$router.push({
          path: '/user/HelpTwo',
          query: { keyword: this.searchKeyword }
        });
      }else{
        this.$message.warning('请输入您要查找的关键词')
      }
    },


  },

  computed: {
    paginatedProducts1() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.products1.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.products1.length / this.itemsPerPage);
    },
    displayedPages() {
      const maxDisplayedPages = 10; // 最多显示的页码数量
      const currentPage = this.currentPage;
      const totalPages = this.totalPages;

      let startPage, endPage;
      if (totalPages <= maxDisplayedPages) {
        startPage = 1;
        endPage = totalPages;
      } else {
        if (currentPage <= Math.floor(maxDisplayedPages / 2)) {
          startPage = 1;
          endPage = maxDisplayedPages;
        } else if (currentPage + Math.floor(maxDisplayedPages / 2) >= totalPages) {
          startPage = totalPages - maxDisplayedPages + 1;
          endPage = totalPages;
        } else {
          startPage = currentPage - Math.floor(maxDisplayedPages / 2);
          endPage = currentPage + Math.floor(maxDisplayedPages / 2);
        }
      }
      return Array.from({length: endPage - startPage + 1}, (_, i) => startPage + i);
    }

  },

  mounted() {
    this.rows = this.chunkArray(this.cards, 3);
    this.loadSearches()
    this.loadShopForGoods()
  }


}

</script>

<style scoped>
a {
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


.imgBox {
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


.imgBox img {
  width: 30%;
  height: 150%;
  margin-left: 30%;
  margin-top: 20%;
  padding-top: 30px;

}

.img1 {
  display: block;
}

.img2 {
  display: none;
}

.img3 {
  display: none;
}


/*求购卡片的样式*/
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


</style>