<template>
  <div>
    <el-header>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
               style="background-color: #2d8477;position: fixed;
   top: 0;left:0;width: 100%;z-index: 999" :router="true"
      >
        <el-menu-item index="/user/HomeOne" style="color: white;font-size: 1vw;"
                      class="Select-btn">首页
        </el-menu-item>
        <el-menu-item index="/user/HelpOne" style="color: white;font-size: 1vw;"
                      class="Select-btn">求购信息
        </el-menu-item>
        <el-menu-item index="/admin" style="color: white;font-size: 1vw"
                      class="Select-btn">管理员登入
        </el-menu-item>
        <el-submenu index="" style="float: right;color: white;font-size: 1vw">
          <template slot="title">
            <img class="user-click"
                 :src= "watchUrl"
                 width="40" height="40"
                 style="background-color: white;border-radius: 50%">
          </template>
          <el-menu-item index="/user/PersonInfo">个人中心</el-menu-item>
          <el-menu-item index="/user/PersonChange">信息修改与发布</el-menu-item>
          <el-menu-item index="/user/MessageCenter">消息中心</el-menu-item>
          <el-menu-item index="/user/OrderCenter">订单中心</el-menu-item>
          <el-menu-item index="/userLogin">安全退出</el-menu-item>
        </el-submenu>
        <el-menu-item index="/user/ShoppingCart" class="Select-btn" style="float: right;
      color: white;font-size: 1vm;width: 8%">
          购物车
        </el-menu-item>
      </el-menu>
      <p style="z-index:999;position: fixed;left: 45%;color: white">欢迎您，{{this.name}}</p>
    </el-header>

    <el-main>
      <router-view></router-view>
    </el-main>

<!--    <el-footer>-->
<!--      <div id="footer" class="footer" style=" background-color:#21675e">-->
<!--        <a style="color: white;font-size: 1.2vm"></a>-->

<!--        <router-link to="/user/AboutUs">-->
<!--          <button class="btn" style="margin-left:13%;">关于我们</button>-->
<!--        </router-link>-->


<!--        <button class="btn" style="margin-left:13%;" @click="dialogVisible = true">联系我们</button>-->
<!--        <el-dialog :modal-append-to-body="false"-->

<!--                   title="联系我们"-->
<!--                   :visible.sync="dialogVisible"-->
<!--                   width="30%"-->
<!--                   @before-close="closeDialog">-->
<!--          <span>如果您遇到问题，可以通过2313046975@qq.com向开发人员反映</span>-->
<!--          <span slot="footer" class="dialog-footer">-->
<!--        <button class="btn" @click="dialogVisible = false">确 定</button>-->
<!--        </span>-->
<!--        </el-dialog>-->

<!--        <router-link to="/user/TableRule">-->
<!--          <button class="btn" style="margin-left:13%;">平台规则</button>-->
<!--        </router-link>-->


<!--        <router-link to="/user/ReportTxt">-->
<!--          <button class="btn" style="margin-left:13%;">廉正举报</button>-->
<!--        </router-link>-->

        <a style="color: white;font-size: 20px;float: right"></a>
      </div>
    </el-footer>
  </div>
</template>
<script>

export default {
  name: "DaoHangLan",
  data() {
    return {
      dialogVisible: false,
      activeIndex: '1',
      activeIndex2: '1',
      backUrl: this.$backUrl,

      imgUrl:'', //用户头像图片
      name:'userName',//用户昵称
    }
  },
  methods: {
    closeDialog(done) {
      // 在这里可以添加过渡效果，使背景逐渐暗下去
      // 完成过渡后调用done()关闭对话框
      done();
    },
    handleStorageChange(event) {
      if (event.key === 'person') {
        // 当 localStorage 的 'person' 发生变化时更新数据
        this.updateDataFromLocalStorage();
      }
    },
    updateDataFromLocalStorage() {
      const personData = JSON.parse(localStorage.getItem('person'));
      if (personData) {
        this.personData = personData;
      }
    },

  },
  computed:{
    watchUrl(){
      return this.backUrl + JSON.parse(localStorage.getItem('person')).avatarUrl
    },
  },
  watch:{

  },

  mounted(){
    // 监听 localStorage 的变化
    window.addEventListener('storage', event => {
      if(event.key === 'person') {
      }
    })

    // 初始化数据
    this.updateDataFromLocalStorage();
  },
  beforeDestroy() {
    // 在组件销毁前移除事件监听
    window.removeEventListener('storage', this.handleStorageChange);
  },
  beforeMount() {
    this.name = JSON.parse(localStorage.getItem("person")).userName
  }
}


</script>

<style scoped>
a {
  text-decoration: none;
}

.btn {
  font-size: 1vw;
  color: white;
  border-radius: 7.8125vw;
  height: 40px;
  padding: 0 1.823vw;
  background-color: #2d8578;
  border: 0.052vw solid #2D8578;
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
  border-radius: 0.78125vw;
  background-color: #000;
  border: inherit;
  transform: translate(-50%, -50%);
  opacity: 0;
  content: ' ';
}

.btn:active::before {
  opacity: 0.1;
}

.Select-btn {
  background-color: #2D8578;
  color: #2c3e50;
}

.Select-btn::before {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  border-radius: 0.78125vw;
  background-color: #000;
  border: inherit;
  transform: translate(-50%, -50%);
  opacity: 0;
  content: ' ';

}

.Select-btn:active::before {
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
  bottom: 0;
  left: 0;
  width: 100%;
  z-index: 999;
}

.custom-dialog .v-modal {
  background-color: rgba(0, 0, 0, 0);
}

</style>


