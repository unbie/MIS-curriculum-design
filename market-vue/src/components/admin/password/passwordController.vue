<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>个人信息</h2>
      </div>
      <div>
        <el-button type="success" icon="el-icon-edit" @click="editMessage">修改信息</el-button>
      </div>
    </el-card>
    <el-dialog :visible="dialogVisible" title="修改个人信息" @close="closeDialog">
      <el-form ref="editForm" :model="editForm" label-width="100px">
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              :action="uploadUrl"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
          >
            <img v-if="editForm.avatarUrl" :src="backUrl+editForm.avatarUrl" class="avatar" alt="头像" style="border-radius: 50%;width:64px;height: 64px"/>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="editForm.adminUsername" ></el-input>
        </el-form-item>
        <el-form-item label="账户">
          <el-input v-model="editForm.adminAccount" @blur="validateAccount"></el-input>
          <div class="error-text" v-if="accountError">{{ accountError }}</div>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" @blur="validatePassword" show-password></el-input>
          <div class="error-text" v-if="passwordError">{{ passwordError }}</div>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editForm.sex" placeholder="请选择">
            <el-option
                v-for="s in optionsSex"
                :key="s.value"
                :label="s.label"
                :value="s.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="editForm.phone" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" :disabled="disableConfirmButton" @click="saveMessage">确认</el-button>
      </div>
    </el-dialog>
    <div>
      <el-card style="margin-top: 4vh">
        <el-row :gutter="10">
          <el-col :span="9"><div class="grid-content bg-purple"> <img alt="用户头像" style="border-radius: 50%;margin-top: 2vh" height="220" width="220" :src='backUrl+this.message.avatarUrl'>
          </div></el-col>
          <el-col :span="9"><div class="grid-content message">
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">昵称</el-col>
              <el-col :span="4" class="el-value"><el-tag class="el-manager-message">{{message.adminUsername}}</el-tag></el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">账户</el-col>
              <el-col :span="4" class="el-value"><el-tag class="el-manager-message">{{message.adminAccount}}</el-tag></el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">性别</el-col>
              <el-col :span="4" class="el-value">
                <el-tag
                    :type="message.sex === 0 ? 'danger' : 'success'"
                    disable-transitions>
                  {{message.sex === 0 ? '女' : '男'}}
                </el-tag>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">等级</el-col>
              <el-col :span="4" class="el-value">
                <el-tag
                    :type="message.roleLevel === 0 ? 'danger' : 'success'"
                    disable-transitions>
                  {{message.roleLevel === 0 ? '超级管理员' : '普通管理员'}}
                </el-tag>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">创建时间</el-col>
              <el-col :span="4" class="el-value"><el-tag class="el-manager-message">{{message.createTime}}</el-tag></el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">联系电话</el-col>
              <el-col :span="4" class="el-value"><el-tag class="el-manager-message">{{message.phone}}</el-tag></el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="6" class="el-class">封锁状态</el-col>
              <el-col :span="4" class="el-value">
                <el-tag
                    :type="message.isLocked === 0 ? 'success' : 'danger'"
                    disable-transitions>
                  {{message.isLocked === 0 ? '正常' : '封锁'}}
                </el-tag>
              </el-col>
            </el-row>
          </div></el-col>

        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "passwordController",
  data() {
    return {
      message:{
        adminUsername:'admin',
        adminAccount:'admin',
        avatarUrl:'dog.jpeg',
        password:"123",
        adminId:'1215',
        roleLevel:0,
        createTime:'2023-07-03 00:15:53',
        phone:'111111111',
        isLocked:0,
        sex:1,
      },

      optionsSex:[{
        value: 0,
        label: '女'
      }, {
        value: 1,
        label: '男'
      }],//性别选择框
      dialogVisible: false,


      editForm: {
        // adminId:'1',
        adminAccount: '',
        adminUsername:'',
        avatarUrl:'',
        password:"",
        // roleLevel:'',
        // createTime:'',
        sex:'',
        phone:'',
        // isLocked:'0',
      },

      uploadUrl: 'http://localhost:8090/carousel/upload', //上传调用的接口
      backUrl: "http://localhost:8090/images/upload/", //回显的路径

      accountError: '', // 账户输入错误提示
      passwordError: '', // 密码输入错误提示
      disableConfirmButton: false, // 是否禁用确认按钮

    }},
  methods:{
    validateAccount() {
      const { adminAccount } = this.editForm;
      if (adminAccount.length < 3) {
        this.accountError = '账户长度必须大于等于三位字符';
        this.disableConfirmButton = true;
      } else {
        this.accountError = '';
        this.disableConfirmButton = false;
      }
    },
    validatePassword() {
      const { password } = this.editForm;
      if (!/^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$/.test(password)) {
        this.passwordError = '密码要求字母和数字结合且长度大于6位';
        this.disableConfirmButton = true;
      } else {
        this.passwordError = '';
        this.disableConfirmButton = false;
      }
    },
    closeDialog(){
      this.dialogVisible = false
      this.resetForm()//重置数据
    },
    editMessage() {

      this.editForm = JSON.parse(JSON.stringify(this.message));

      this.dialogVisible = true;

    },
    resetForm() {
      // 重置表单
      this.editForm = {
        adminAccount: '',
        adminUsername:'',
        avatarUrl:'',
        password:"",
        sex:'',
        phone:'',
      }
    },
    saveMessage() {
      let newMessage = null;
      newMessage = {
        adminUsername:this.editForm.adminUsername,
        adminAccount:this.editForm.adminAccount,
        avatarUrl:this.editForm.avatarUrl,
        password:this.editForm.password,
        adminId:this.message.adminId,
        roleLevel:this.message.roleLevel,
        createTime:this.message.createTime,
        phone:this.editForm.phone,
        isLocked:this.message.isLocked,
        sex:this.editForm.sex,
      }

      const adminAccount = this.message.adminAccount
      const password = this.message.password

      this.$axios({
        url:this.$http + '/admin/update',
        data:newMessage,
        method:'post',
        headers:{
          token:localStorage.getItem("token")
        }
      }).then(res=>res.data).then(res=>{
        if(res.resultCode === 200){
          localStorage.setItem('admin',JSON.stringify(newMessage))
          this.load()
        }else{
          alert('更新数据失败')
        }
      })


      this.$message.success('修改个人信息成功');

      let flag = false
      // 如果修改了账户或者密码，则弹出消息框提示用户请重新登录，并在其点击确认按钮之后，退出登录
      if (newMessage.adminAccount !== adminAccount || newMessage.password !== password) {
        flag = true
        this.$confirm('您已修改账户或密码，请重新登录', '提示', {
          confirmButtonText: '重新登录',
          type: 'warning'
        }).then(() => {
          // 清除登录信息并退出登录操作
          localStorage.removeItem('admin');
          this.$router.push('/admin');

        }).catch(() => {
          // 用户点击取消按钮
          // 可以不做任何操作，或者根据需求执行其他逻辑
        });
      }


      this.dialogVisible = false; // 关闭编辑对话框
      if(!flag){
        this.$router.go(0)
      }
    },

    handleUploadSuccess(response) {
      // 处理图片上传成功回调
      // 获取上传成功后的图片地址，并赋值给表单数据
      this.editForm.avatarUrl = response.data
    },
    beforeUpload(file) {
      // 图片上传前的校验，例如图片类型、大小等
      // 返回 false 可取消上传
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('只允许上传 JPG/PNG 格式的图片');
        return false;
      }

      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB');
        return false;
      }
      return true;
    },

    //初始化
    load(){
      //注意这里要转json
      let adminMessage = JSON.parse(localStorage.getItem('admin'));
      let formattedCreateTime;

      if (typeof adminMessage.createTime === 'string') {
        formattedCreateTime = adminMessage.createTime; // 不执行转化，保持原格式
      } else {
        const createTime = new Date(adminMessage.createTime.time);
        const year = createTime.getFullYear();
        const month = String(createTime.getMonth() + 1).padStart(2, '0');
        const day = String(createTime.getDate()).padStart(2, '0');
        const hours = String(createTime.getHours()).padStart(2, '0');
        const minutes = String(createTime.getMinutes()).padStart(2, '0');
        const seconds = String(createTime.getSeconds()).padStart(2, '0');
        formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      }
      adminMessage.createTime = formattedCreateTime
      this.message = adminMessage
    }
  },

  // 在加载时，将token里面的值赋给message
  mounted() {
    this.load()
  }


}

</script>

<style scoped>

.el-class{
  margin-top: 0.8vh;
  font-size: 1.8vh;
  margin-left: 7vw;
}
.bg-purple{
  margin-left: 8vw;
}
.el-value{
  padding: 0;
  margin-left: 2.5vw;
  vertical-align: center;
}
.message{
  margin-left: 0;
  padding-left: 2vw;
}
.el-manager-message{
  font-size: 1.7vh;
}

.error-text {
  color: red;
  margin-top: 5px;
  font-size: 12px;
}

</style>