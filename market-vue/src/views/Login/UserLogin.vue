<template>
  <div id="building">
  <div>
  <el-form
      :model="form"
      status-icon
      :rules="rules"
      ref="form"
      label-width="100px"
      class="login-container"
      style="height: 80%"
  >
    <img src="../../assets/Userimg/logo.jpg" style="width: 50%;height: auto;margin-left: 6vw;">
    <!-- 系统登录 -->
    <h3 class="login_title">线上校园二手物品交易信息管理系统</h3>

    <!-- 用户名 -->
    <el-form-item label="学号" label-width="80px" prop="personAccount" class="personAccount">
      <el-input
          type="input"
          v-model="form.personAccount"
          auto-complete="off"
          placeholder="请输入学号"
      ><i class="el-icon-service" ></i></el-input>
    </el-form-item>

    <!-- 密码 -->
    <el-form-item label="密码" label-width="80px" prop="password">
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

    <el-form-item class="login_submit">
      <el-button  @click="login" @keyup.enter.native="login" class="login_button">登录</el-button>
    </el-form-item>
     <el-form-item style="margin-top: 2%">
       <el-button   @keyup.enter.native="register" class="login_button"  type="text" @click="displayRegister">注册</el-button>
    </el-form-item>

  </el-form>



<!--  注册弹窗  -->
    <!-- Form -->
    <el-dialog title="注册" :visible.sync="dialogFormVisible">
      <el-form :model="formX">
        <el-form-item label="学校" :label-width="formLabelWidth">
          <!-- 学校选择器 -->
          <el-select v-model="formX.campusName" placeholder="请选择学校">
            <!-- 学校选项 -->
            <el-option
                v-for="campus in campuses"
                :key="campus.campusId"
                :label="campus.campusName"
                :value="campus.campusName"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="formX.account" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="formX.userName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="formX.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <!--            <el-input v-model="editItem.sex"></el-input>-->
          <el-select v-model="formX.sex" placeholder="请选择">
            <el-option
                v-for="s in optionsSex"
                :key="s.value"
                :label="s.label"
                :value="s.value">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelRegister">取 消</el-button>
        <el-button type="primary" @click="personRegister">确 定</el-button>
      </div>
    </el-dialog>

  </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: 'UserLogin',
  data() {
    return {
      optionsSex:[{
        value: 0,
        label: '女'
      }, {
        value: 1,
        label: '男'
      }],//性别选择框
      dialogFormVisible: false,
      captchaImageUrl: '',
      formX: {
        userName: '',
        account: '',
        password: '',
        sex:0,
        campusName:'',
      },
      formLabelWidth: '120px',


      form: {},
      rules: {
        personAccount: [
          {
            required: true,
            message: '请输入学号',
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
      },
      counts:0,//输错密码/账户的次数

      campuses: [], // 学校列表
    }
  },
  methods: {
    login() {
      //前端验证验证码
      if (this.form.captcha !== this.captcha && this.counts >= 3) {
        this.$message.error("验证码错误")
        return
      }
      let _this = this
      let data = new FormData();
      data.append('account', this.form.personAccount)
      data.append('password', this.form.password)
      axios({
        method: 'post',
        url: this.$http + '/person/personLogin',
        data: data,
        header: {
          'content-type': 'application/x-www-form-urlencoded',
        },
      }).then(function (response) {
        // 登录成功，保存token 和用户信息
        if (response.data.token !== '' && response.data.token !== 'undefined') {
          _this.$message({message: response.data.msg, type: 'success',})
          localStorage.setItem("token", response.data.token)
          localStorage.setItem("person",JSON.stringify(response.data.person));
          localStorage.setItem("passwordNoMD5",_this.form.password)
          _this.$router.replace('/user/HomeOne')
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
        this.login(); // 定义的登录方法
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
      }).catch(() => {
        this.$message.error('刷新验证码失败')
      });
    },
    displayRegister(){
      this.resetFormX()
      this.dialogFormVisible = true
    },
    cancelRegister(){
      this.dialogFormVisible = false;
      this.resetFormX();
    },
    personRegister(){
      //用户注册
      let _this = this
      this.$axios({
        url:this.$http + "/person/personRegister",
        method:'post',
        data:this.formX,
        header:{
          'content-type': 'application/x-www-form-urlencoded',
        }
      }).then(function (response) {
        // 登录成功，保存token 和用户信息
        if (response.data.token !== '' && response.data.token !== undefined && response.data.token !== null) {
          _this.$message({message: response.data.msg, type: 'success',})
          localStorage.setItem("token", response.data.token)
          localStorage.setItem("person",JSON.stringify(response.data.person));
          localStorage.setItem("passwordNoMD5",_this.form.password)
          _this.$router.replace('/user/HomeOne')
          setTimeout(() => {
          }, 200);
        } else {
          _this.$message({message: response.data.msg, type: 'error',})
          return
        }
      });

      this.dialogFormVisible = false;
    },
    resetFormX(){
      this.formX.userName= ''
      this.formX.account= ''
      this.formX.password= ''
      this.formX.sex= 0
      this.formX.campusName= ''
    },
    // 获取学校列表
    fetchCampuses() {
      this.$axios.get(this.$http+'/near-sell/listCampusesWithNoToken').then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
          this.campuses = res.data
        }else{
          alert('获取数据失败')
        }
      })
    },
  },
  mounted() {
    // 绑定监听事件
    window.addEventListener("keydown", this.keyDown);
    // 页面加载时请求验证码图片
    this.refreshCaptcha();
    // 获取学校列表
    this.fetchCampuses()
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
  background:url("../../../../market/src/main/resources/static/images/upload/background-image1.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 25vw;
  //height: 38vw;
  padding: 3.5vw 35px 15px 35px;
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