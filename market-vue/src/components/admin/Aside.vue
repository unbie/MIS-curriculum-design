<template>
  <el-menu default-active="/adminMain/home" :collapse="isCollapse" :collapse-transition="false"
    router>
    <el-menu-item-group style="height: 8vh"><span style="font-size: 10vm">线上校园二手物品交易信息管理系统</span></el-menu-item-group>
    <el-menu-item index="/adminMain/home" class="menu-item-align-left"><i class="el-icon-s-home"></i><span>首页</span></el-menu-item>
    <el-menu-item-group class="menu-item-align-left">
    <template slot="title">管理模块</template>
    </el-menu-item-group>
    <el-menu-item index="/adminMain/personController" class="menu-item-align-left"><i class="el-icon-user-solid"></i><span>用户管理</span></el-menu-item>
    <el-menu-item index="/adminMain/orderController" class="menu-item-align-left"><i class="el-icon-sold-out"></i><span>订单管理</span></el-menu-item>
    <el-menu-item index="/adminMain/commentController" class="menu-item-align-left"><i class="el-icon-s-comment"></i><span>评论管理</span></el-menu-item>
    <el-menu-item index="/adminMain/goodController" class="menu-item-align-left"><i class="el-icon-s-goods"></i><span>商品管理</span></el-menu-item>
    <el-menu-item index="/adminMain/needGoodsController" class="menu-item-align-left"><i class="el-icon-goods"></i><span>求购管理</span></el-menu-item>
    <el-menu-item index="/adminMain/categoryController" class="menu-item-align-left"><i class="el-icon-s-grid"></i><span>分类管理</span></el-menu-item>
    <el-menu-item index="/adminMain/logController" class="menu-item-align-left"><i class="el-icon-s-management"></i><span>日志管理</span></el-menu-item>
    <el-menu-item-group class="menu-item-align-left">
    <template slot="title">首页配置</template>
    </el-menu-item-group>
    <el-menu-item index="/adminMain/carouselSetting" class="menu-item-align-left"><i class="el-icon-picture"></i><span>轮播图设置</span></el-menu-item>
<!--    <el-menu-item index="/adminMain/guessYouLikeSetting" class="menu-item-align-left"><i class="el-icon-star-on"></i><span>猜你喜欢配置</span></el-menu-item>-->
    <el-menu-item index="/adminMain/hotSaleNearbySetting" class="menu-item-align-left"><i class="el-icon-s-shop"></i><span>附近热卖配置</span></el-menu-item>
    <el-menu-item-group class="menu-item-align-left">
    <template slot="title">系统管理</template>
    </el-menu-item-group>
    <el-menu-item index="/adminMain/passwordSetting" class="menu-item-align-left"><i class="el-icon-setting"></i><span>个人信息</span></el-menu-item>
    <el-menu-item v-if="!roleLevel" index="/adminMain/managerController" class="menu-item-align-left"><i class="el-icon-setting"></i><span>管理员管理</span></el-menu-item>
    <el-menu-item index="/admin" class="menu-item-align-left"><i class="el-icon-remove"></i><span>安全退出</span></el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      roleLevel:0
    }
  },
  props:{
    isCollapse:Boolean,

  },
  methods:{

  },
  mounted() {
    this.roleLevel = JSON.parse(localStorage.getItem('admin')).roleLevel
  }
}


</script>

<style scoped>
.el-menu{
  background-color: #2D8578;
  border: 0;
}
.el-menu-item-group{
  background-color: #21675e;
  color: white;
}
.el-submenu{
  background-color: #21675e;
}
.el-menu-item{
  background-color: #2D8578;
  color: white;
}
.el-menu-item:hover{
  color: black;
}
.el-menu-item:focus{
  background-color: cadetblue;
  color: black;
}
.el-menu-item:active{
  background-color: cadetblue;
  color: black;
}
.red-text{
  color: red;
}
.menu-item-align-left {
  text-align: left;
  //width: 24px;
}
.el-menu--collapse {
  //width: 64px;
  padding: 0;
}
.el-aside{
  overflow:hidden;
}


</style>