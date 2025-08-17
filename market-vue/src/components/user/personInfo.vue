<template>
  <div>

    <!--    用户个人信息-->
    <img :src="avatarPath" class="round-image" style="position: absolute; top:8%;left:10%" width="350" height="350">
    <div class="square" style="position: absolute; top: 12%; left: 45%;">
      <p>账号</p>
    </div>
    <div style="position: absolute; top: 11%; left: 52%;">
      <p>{{ userIng.account }}</p>
    </div>


    <div class="square" style="position: absolute; top: 20%; left: 45%;">
      <p>昵称</p>
    </div>
    <div style="position: absolute; top: 19%; left: 52%;">
      <p>{{ userIng.userName }}</p>
    </div>


    <div class="square" style="position: absolute; top: 28%; left: 45%;">
      <p>性别</p>
    </div>
    <div style="position: absolute; top: 27%; left: 52%;">
        <el-tag
            :type="userIng.sex === 1 ? 'success' : 'danger'"
            disable-transitions>{{userIng.sex === 1 ? '男' : '女'}}
        </el-tag>
    </div>


<!--    这个地方报错需要解决-->
    <div class="square" style="position: absolute; top: 36%; left: 45%;">
      <p>学校</p>
    </div>
    <div style="position: absolute; top: 35%; left: 52%;">
      <p>{{ personCampus}}</p>
    </div>


    <div class="square" style="position: absolute; top: 44%; left: 45%;">
      <p>注册时间</p>
    </div>
    <div style="position: absolute; top: 43%; left: 52%;">
      <p>{{ userIng.registerDate }}</p>
    </div>



    <div class="square2" style="background-color:#9cc9b6; position: absolute; top: 60%; left: 5%;">
      <p >正在出售</p>
    </div>

    <div class="parent-container" style="height: 50vw;">
      <el-carousel :interval="4000" class="el-carousel" type="card" height="300px" style="position: relative;top:60%">
        <el-carousel-item v-for="item in onSaleImgList" :key="item.saleId">
            <img @click="toGoodInfo(item)" style="object-fit: scale-down;width: 100%;height: 100%;" :src="item.url" class="image">

        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="square2" style="background-color:#9cc9b6; position: absolute; top: 120%; left: 5%;">
      <p >正在求购</p>
    </div>
    <div class="parent-container" style="height: 50vw;">
    <el-carousel :interval="4000" class="el-carousel" type="card" height="300px" style="position: relative;top:20%">
      <el-carousel-item v-for="item in onSeekImgList" :key="item.seekId">
          <img @click="toBuyInfo(item)" style="object-fit: scale-down;width: 100%;height: 100%;" :src="item.url" class="image">
      </el-carousel-item>
    </el-carousel>
    </div>

    <div class="square" style="background-color:#9cc9b6; position: absolute; top: 180%; left: 5%;">
      <p >个人评价</p>
    </div>

    <div style="position: absolute;top:181%;left:15%" class="star">
      <span class="star-item" v-for="(itemClass, index) in itemClass" :key="index" :class="itemClass"> </span>
    </div>
    <p style="position: absolute;top:180%;left:27%" v-if="score !== null && score !== undefined && !isNaN(score)">{{score}}</p>
    <p style="position: absolute;top:180%;left:27%" v-else>暂无评价</p>

    <div class="parent-container" style="height: 450px;">

    </div>


    <div style="position:absolute;top:190%;left:10%;">
      <i class="el-icon-edit-outline"
         style="background-color: #6bae91;font-size: 25px;color:white"> 评价</i>>
    </div>

    <div class="chat-container" style="position: absolute; top: 195%; left: 15%; transform: translateX(-50%);">
      <div class="chatWith">
        <template v-for="(comment, index) in commentList">
          <div class="comment-container" :key="index">
            <div class="user">
              <img class="user-click"
                   src="../../assets/Userimg/default.jpeg"
                   width="40"
                   height="40">
            </div>
            <textarea :cols="80"
                      rows="2"
                      disabled
                      :style="`font-size: 14px; resize: none; color: #21675e;`"
                      :placeholder="comment.modifiedComments">
        </textarea>
          </div>
        </template>
      </div>
    </div>



  </div>
</template>

<script>

const LENGTH = 5;
const CLS_ON = "on";
const CLS_HALF = "half";
const CLS_OFF = "off";

export default {
  name: 'PersonInfo',

  data(){
    return{

      score: 0,
      activeIndex: '1',
      activeIndex2: '1',

      message:{
        personId:0,
        userName:'',
        sex:0,
        account:'',
        avatarUrl:'',
        campusId:1,
        createTime:'2023-07-03 00:15:53',
        isLocked:0,
        password:'',
        registerDate:'2023-07-03 00:15:53',
      },

      //当前用户的学校
      personCampus:'',

      //所有学校的列表
      campusList:[{
        campusId:0,
        campusName:'',
        createTime:'',
      }],

      //所有有关当前界面用户的评论的列表
      commentList:[],
      textareaList: [],

      //上一个界面传递过来的用户信息
      //给其他页面传递用户信息过来的一个接口
      //作用在于 能够实现查看别人的个人主页
      //当查看别人的个人主页时，点击别人的头像，跳转路由，并在跳转的同时将用户的信息传递过来，一般是传递personId
      preUser:{
        personId:0,
        userName:'',
        sex:0,
        account:'',
        avatarUrl:'',
        campusId:1,
        createTime:'2023-07-03 00:15:53',
        isLocked:0,
        password:'',
        registerDate:'2023-07-03 00:15:53',
      },

      //当前界面用户信息
      userIng:{
        personId:0,
        userName:'',
        sex:0,
        account:'',
        avatarUrl:'',
        campusId:1,
        createTime:'2023-07-03 00:15:53',
        isLocked:0,
        password:'',
        registerDate:'2023-07-03 00:15:53',
      },

      //当前用户所对应的所有商品的图片
      allImgList:[],
      //正在出售商品的图片
      onSaleImgList:[],
      //正在求购商品的图片
      onSeekImgList:[],
      //图片回显的路径
      backUrl: "http://localhost:8090/images/upload/",
      screenWidth:0
    }
  },
  methods:{
    toGoodInfo(item){
      this.$router.push({
        path: '/user/GoodInformation',
        query: {productId: item.goodId}
      })
    },
    toBuyInfo(item){
      this.$router.push({
        path: '/user/BuyInformation',
        query: {productId: item.goodId}
      })
    },
    skip(){
      this.$router.push({name:'PersonChange'})
    },

    //界面初始化
    loadCampus(){
      this.$axios({
        url:this.$http + '/near-sell/listCampuses',
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.campusList=res.data
          this.personCampus = this.campusList[this.userIng.campusId - 1].campusName
        }else{
          alert('获取数据失败')
        }
      })
    },
    //获取当前personId所对应的所有商品所对应的首张图片
    loadImg(){
      this.$axios({
        url:this.$http + '/good/getImg?personId='+this.userIng.personId,
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.allImgList=res.data
          this.distinguishPictures();
        }else{
          alert('获取数据失败')
        }
      })
    },
    load(){
      if(this.preUser.personId === 0 || this.preUser.personId === undefined){
        //用户信息初始化
        let userMessage = JSON.parse(localStorage.getItem('person'))
        let formattedCreateTime;
        if (typeof userMessage.registerDate === 'string') {
          formattedCreateTime = userMessage.registerDate; // 不执行转化，保持原格式
        } else {
          const registerTime = new Date(userMessage.registerDate.time);
          const year = registerTime.getFullYear();
          const month = String(registerTime.getMonth() + 1).padStart(2, '0');
          const day = String(registerTime.getDate()).padStart(2, '0');
          const hours = String(registerTime.getHours()).padStart(2, '0');
          const minutes = String(registerTime.getMinutes()).padStart(2, '0');
          const seconds = String(registerTime.getSeconds()).padStart(2, '0');
          formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        }
        userMessage.registerDate = formattedCreateTime

        this.message = userMessage
        this.userIng = userMessage
        this.loadImg()
        this.loadComment()
      }else {
        //通过personId获取到对应的person
        //调用后端
        this.$axios({
          url:this.$http + '/person/getAPerson?personId=' + this.preUser.personId,
          method:'get',
          headers:{
            token:localStorage.getItem("token")
          }
        }).then(res=>res.data).then(res=>{

          if(res.resultCode===200){
            this.preUser = res.data;
            this.userIng = {...this.preUser}
            this.loadImg()
            this.loadComment()
          }else{
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        })
      }
    },
    //获取当前personId所对应的所有评论以及评分
    loadComment(){
      this.$axios({
        url:this.$http + '/comment/getCommentById?personId='+this.userIng.personId,
        method:"get",
        headers:{
          token:localStorage.getItem("token")
        },
      }).then(res=>res.data).then(res=>{
        if(res.resultCode===200){
          this.commentList=res.data
          // 计算评论列表中 Comment.stars 的平均值
          const totalStars = this.commentList.reduce((sum, comment) => sum + comment.stars, 0);
          const averageStars = totalStars / this.commentList.length;
          // 将平均值保留两位小数
          const formattedAverageStars = averageStars.toFixed(2);
          this.score = formattedAverageStars;
           }else{
          alert('获取数据失败')
        }
      })
    },
    distinguishPictures(){
      this.allImgList.forEach(item => {
        // 在这里处理每个图片对象item
        // 其他逻辑操作
        if (item.good.isSale === 1) {
          this.onSaleImgList.push({
            saleId: this.onSaleImgList.length + 1, // 自增的 saleId
            goodId:item.good.goodId,
            url: this.backUrl+item.imgUrl
          });
        } else {
          this.onSeekImgList.push({
            seekId: this.onSeekImgList.length + 1, // 自增的 seekId
            goodId:item.good.goodId,
            url: this.backUrl+item.imgUrl
          });
        }
      });
    },

  },
  beforeMount() {
    this.load();
    this.loadCampus();
  },
  computed: {
    avatarPath() {
      if (this.userIng.avatarUrl !=='') {
        return this.backUrl + this.userIng.avatarUrl;
      } else {
        return require("../../assets/Userimg/default.jpeg");
      }
    },
    itemClass() {
      if (!this.score) {
        return []; // 如果 score 为空，返回空数组
      }
      let result = [];
      let score = Math.floor(this.score * 2) / 2;
      let hasDecimal = score % 1 !== 0;
      let integer = Math.floor(score);

      for(let i = 0; i < integer; i++) {
        result.push(CLS_ON);
      }

      // 是否有半个星星
      if(hasDecimal) {
        result.push(CLS_HALF);
      }

      // 补齐
      while(result.length < LENGTH) {
        result.push(CLS_OFF);
      }
      return result
    }
  },
  created() {
    this.preUser.personId = this.$route.query.personId
  }
}
</script>

<style scoped>
.square {
  width: 80px;
  height: 40px;
  border-radius: 10px;
  background-color: #6bae91;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  color: black;
  opacity: 0.5;
}
.square p {
  color: black;
  font-size: 15px;

  margin: 0;
}
.square2 {
  width: 100px;
  height: 40px;
  border-color: #2b5b55;
  border-radius: 10px;
  background-color: white;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  color: black;
  opacity: 0.75;
}
.square2 p {
  color: white;
  font-size: 16px;
  margin: 0;
}

.zoom-image {
  transition: transform 0.2s ease-in-out;
}
.zoom-image:hover {
  transform: scale(1.2);
}

.round-image {
  border-radius: 50%;
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

.location{
  width: 100px;
  height: 50px;
  border-radius: 10px;
  background-color: #6bae91;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  color:white;
}


.el-carousel__item h3 {
  color: darkseagreen;
  background-color: #9cc9b6;
  font-size: 15px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  height: 300px;
  width: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.el-carousel {
  position: relative;
  top: 80%;
}








.star .star-item{
  display: inline-block;
  width: 30px;
  height: 30px;
  margin-right: 3px;
  background-repeat: no-repeat;
  background-size: 30px 30px;
}

/* 三种图片类型*/
.star .on{
  background-image: url(../../assets/Userimg/full-star.png);
}
.star .half{
  background-image: url(../../assets/Userimg/half-star.png);
}
.star .off{
  background-image: url(../../assets/Userimg/empty-star.png);
}

.chat-container{
  margin-top: 0vh;
}

.comment-container {
  display: flex;
  align-items: center;
  position: relative;
  margin-left: 20vw;
}

.user {
  flex-shrink: 0;
  margin-right: 10px;
}

textarea {
  position: absolute;
  top: 0;
  left: 60px; /* 调整适当的间距 */
  /* 其他样式... */
}

</style>