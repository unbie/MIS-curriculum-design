<template>
  <div id="building">
  <el-form
      :model="form"
      status-icon
      :rules="rules"
      ref="form"
      label-width="100px"
      class="login-container"
  >
    <img src="../../assets/Userimg/logo.jpg" style="width: 50%;height: auto;margin-left: 6vw;">
    <!-- 系统登录 -->
    <h3 class="login_title">线上校园二手物品交易管理员平台</h3>

    <!-- 用户名 -->
    <el-form-item label="账    号" label-width="80px" prop="adminAccount" class="adminAccount">
      <el-input
          type="input"
          v-model="form.adminAccount"
          auto-complete="off"
          placeholder="请输入账号"
      ><i class="el-icon-service" ></i></el-input>
    </el-form-item>

    <!-- 密码 -->
    <el-form-item label="密    码" label-width="80px" prop="password">
      <el-input
          type="password"
          v-model="form.password"
          auto-complete="off"
          placeholder="请输入密码"
      ></el-input>
    </el-form-item>

    <!-- 验证码 -->
    <el-form-item v-if="counts >=3 " label="验证码" label-width="80px" prop="captcha">
      <el-input
          v-model="form.captcha"
          auto-complete="off"
          placeholder="请输入验证码"
      ></el-input>
      <div style="margin-top: 1vh">
        <img :src="captchaImageUrl" alt="验证码" @click="refreshCaptcha" style="cursor: pointer;">
      </div>
    </el-form-item>

    <!-- 登录按钮 -->
    <el-form-item class="login_submit">
      <el-button  @click="login2" @keyup.enter.native="login2" class="login_button" >登录</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: 'AdminLogin',
  data() {
    return {
      form: {
        adminAccount: '',
        password: '',
        captcha: ''
      },
      captchaImageUrl: '',
      rules: {
        adminAccount: [
          {
            required: true,
            message: '请输入账号',
            trigger: 'blur',
          },
          {
            min: 3,
            message: '学号长度不能小于3',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
        captcha: [
          {
            required: true,
            message: '请输入验证码',
            trigger: 'blur',
          },
        ],
      },
      tokenItem:'',
      captcha:'', //验证码

      counts:0, //输错密码/账户的次数
    }
  },
  methods: {
    login2() {
      //前端验证验证码
      if (this.form.captcha !== this.captcha && this.counts >= 3) {
        this.$message.error("验证码错误")
        return
      }
      let _this = this
      let data = new FormData();
      data.append('account', this.form.adminAccount)
      data.append('password', this.form.password)
      data.append('captcha', this.form.captcha)
      axios({
        method: 'post',
        url: this.$http + '/admin/adminLogin',
        data: data,
        header: {
          'content-type': 'application/x-www-form-urlencoded',
        },
      }).then(function (response) {
        // 登录成功，保存token 和用户信息
        if (response.data.token !== '') {
          _this.$message({message: response.data.msg, type: 'success',})
          localStorage.setItem("token", response.data.token)
          localStorage.setItem("admin",JSON.stringify(response.data.admin));
          //记录下当前用户输入的密码 可以盗号hhhh 方便之后显示
          localStorage.setItem("passwordNoMD5",_this.form.password);
          _this.$router.replace('/adminMain/home')
          setTimeout(() => {
          }, 200);
        } else {
          _this.$message({message: response.data.msg, type: 'error',})
          _this.counts++
          return
        }
      });
    },
    // 点击回车键登录
    keyDown(e) {
      // 回车则执行登录方法 enter键的ASCII是13
      if (e.keyCode === 13) {
        this.login2(); // 定义的登录方法
      }
    },

    // 刷新验证码
    refreshCaptcha() {
      const timestamp = new Date().getTime();
      const captchaUrl = this.$http + `/captcha?${timestamp}`;

      axios({
        url: captchaUrl,
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(response => {
        this.captcha = response.data.captcha; // 更新验证码文本
        this.captchaImageUrl = `data:image/jpeg;base64, ${response.data.imageBase64}`; // 更新验证码图片的 Base64 编码字符串
      }).catch(error => {
      });
    }


  },

  mounted() {
    // 绑定监听事件
    window.addEventListener("keydown", this.keyDown);
    // 页面加载时请求验证码图片
    this.refreshCaptcha();
  },
  destroyed() {
    // 销毁事件
    window.removeEventListener("keydown", this.keyDown, false);
  },
}
</script>

<style lang="less" scoped>

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}

body{
  margin:0;
  padding:0;
  border:0
}

#building{
  background:url("../../../../market/src/main/resources/static/images/upload/background-image2.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login_title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #2D8578;

}
.login_submit {
  margin: 10px auto 0 auto;
}
.login_button{
  background-color: #2D8578;
  font-size: 15px;
  color: white;
  padding: 13px 62px;
}
.login_button:hover{
  background-color:#21675e ;
  color: white;
}
.login_button:focus{
  background-color: #21675e;
  color: white;
}

</style>