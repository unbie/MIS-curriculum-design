<template>
  <div>

    <!--  个人信息展示  -->
    <img :src='avatarPath' class="round-image" style="position: absolute; top:8%;left:10%" width="350" height="350">
    <div class="square" style="position: absolute; top: 12%; left: 45%;">
      <p>昵称</p>
    </div>
    <div class="" style="position: absolute; top: 12%; left: 55%;">
      <p>{{ userIng.userName }}</p>
    </div>

    <div class="square" style="position: absolute; top: 20%; left: 45%;">
      <p>性别</p>
    </div>
    <div class="" style="position: absolute; top: 21%; left: 55%;">
      <el-tag
          :type="userIng.sex === 0 ? 'danger' : 'success'"
          disable-transitions>{{ userIng.sex === 0 ? '女' : '男' }}
      </el-tag>
    </div>

    <!--  这个地方还有问题-->
    <div class="square" style="position: absolute; top: 28%; left: 45%;">
      <p>学校</p>
    </div>
    <div class="" style="position: absolute; top: 28%; left: 55%;">
      <p>{{ campusList[userIng.campusId - 1].campusName }}</p>
      <!--    <p>{{personCampus}}</p>-->
    </div>
    <div class="square" style="position: absolute; top: 36%; left: 45%;">
      <p>密码</p>
    </div>
    <div class="" style="position: absolute; top: 36%; left: 55%;">
      <p>{{ displayPassword }}</p>
    </div>

    <el-popover
        placement="top-start"
        width="200"
        trigger="hover"
        content="点击可查看和修改收货地址">
      <button @click="showModal" class="square" slot="reference"
              style="border:none;position: absolute;top:44%;left:45%">地址
      </button>
    </el-popover>


    <button id="infobtn" type="success" @click="editMessage" style="position: absolute; top: 52%; left: 60%;">编辑信息
    </button>

    <!-- 编辑个人信息 -->
    <el-dialog :visible="PersondialogVisible" title="修改个人信息" @close="closePersonDialog">
      <el-form ref="editForm" :model="editUserIng" label-width="100px">
        <el-form-item label="头像">
          <el-upload
              :action="uploadUrl"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload">
            <img style="border-radius: 50%;margin-top: -2vh;margin-left: 2vw" height="80vh" width="80vh"
                 v-if="editUserIng.avatarUrl"
                 :src="backUrl+editUserIng.avatarUrl" class="avatar" alt="头像"/>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="editUserIng.userName" name="userName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <!--        <el-input v-model="PersoneditForm.sex" name="sex"></el-input>-->
          <el-select v-model="editUserIng.sex" placeholder="请选择">
            <el-option
                v-for="s in optionsSex"
                :key="s.value"
                :label="s.label"
                :value="s.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <!--        <el-input v-model="PersoneditForm.campusId" name="campusId"></el-input>-->
          <!-- 学校选择器 -->
          <el-select v-model="editUserIng.campusId" placeholder="请选择学校">
            <!-- 学校选项 -->
            <el-option
                v-for="campus in campusList"
                :key="campus.campusId"
                :label="campus.campusName"
                :value="campus.campusId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="displayPassword" name="password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">取消</el-button>
        <el-button type="primary" @click="PersonsaveMessage">确认</el-button>
      </div>
    </el-dialog>


    <!--收货信息的弹窗-->
    <div v-if="isModalVisible" class="modal">
      <div class="modal-content">
        <h3 style="color: #2b5b55">编辑收货信息</h3>
        <div v-for="(address, index) in newItemAddresses" :key="index">
          <div class="input-group">
            <div class="input-group">
              <label for="name" style="left: 0%;">姓名：</label>
              <input type="text" id="name" v-model="address.shoppingName"
                     :class="{'invalid': !validateName(address.shoppingName)}" size="10">
            </div>
            <div class="input-group">
              <label for="phone">电话：</label>
              <input type="tel" id="phone" v-model="address.shoppingPhone"
                     :class="{'invalid': !validatePhone(address.shoppingPhone)}"><br>
            </div>
            <div class="input-group">
              <label for="address">地址：</label>
              <textarea id="address" rows="3" v-model="address.shippingAddress"
                        :class="{'invalid': !validateAddress(address.shippingAddress)}"></textarea><br>
            </div>
            <button style="margin-left: 20px" @click="deleteAddress(index)">删除</button>
          </div>
        </div>
        <button @click="addAddress">添加新地址</button>
        <div class="modal-footer">
          <button @click="saveAddress">保存</button>
          <button @click="cancelChanges">取消</button>
        </div>
      </div>
    </div>

    <!--商品管理的表格-->
    <div class="square" style="position: absolute; top: 64%; left: 2%;">
      <p>产品管理</p>
    </div>
    <!--添加按钮-->
    <button @click="openAddDialog" style="position:absolute;top:73%;left:2%;">添加商品</button>
    <el-dialog :visible.sync="NewDialogForm" title="添加商品">

      <!--  增加-->
      <el-form :model="newForm">
        <el-form-item label="商品图片">
          <el-upload
              class="avatar-uploader"
              :action="uploadUrl"
              :file-list="changeImgList.image"
              list-type="picture-card"
              :on-success="handleUploadImgSuccess"
              :before-upload="beforeUpload"
              multiple
              :on-remove="handleRemove"
              :limit="5">
            <img style="border-radius: 50%;margin-top: -2vh;margin-left: -1vw" height="80vh" width="80vh"
                 v-for="(img,index) in changeImgList"
                 :src="backUrl+img" class="imgUrl" alt="商品图片" :key="index"/>
          </el-upload>
        </el-form-item>
        <el-form-item label="请输入商品名称">
          <el-input v-model="newForm.goodName" name="goodName"></el-input>
        </el-form-item>
        <el-form-item label="请选择该商品的属性">
          <input v-model="newForm.isSale" type="radio" id="11" value="1" name="1"><label for="11">出售</label>
          <input v-model="newForm.isSale" type="radio" id="00" value="0" name="0"><label for="00">求购</label>
        </el-form-item>
        <el-form-item label="商品类别">
<!--          <el-input v-model="newForm.categoryId" name="category"></el-input>-->
          <el-select v-model="newForm.categoryId" placeholder="请选择商品类别"
                     @change="handleCategoryChange">
            <!-- 类别选项 -->
            <el-option
                v-for="category in categoryList"
                :key="category.categoryId"
                :label="category.categoryName"
                :value="category.categoryId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="输入商品的数量">
          <el-input v-model="formattedCounts" name="counts"></el-input>
        </el-form-item>
        <el-form-item label="输入商品简介">
          <el-input v-model="newForm.description" name="description"></el-input>
        </el-form-item>
        <el-form-item label="输入商品价格/期望的求购价格">
          <el-input v-model="newForm.price" name="price"></el-input>
        </el-form-item>
        <el-form-item label="输入商品邮费/期望的邮费">
          <el-input v-model="newForm.expressPrice" name="expressPrice"></el-input>
        </el-form-item>
        <el-button @click="cancelNew">取 消</el-button>
        <el-button type="primary" @click="addRow()">确 定</el-button>
      </el-form>
    </el-dialog>


    <el-table :data="tableData" :border="true" style="margin-top: 70vh ;margin-bottom: 2vh">
      <el-table-column prop="good.isSale" label="商品属性">
        <template slot-scope="scope">
          <el-tag :type="scope.row.good.isSale === 0 ? 'danger' : 'success'" disable-transitions>
            {{ scope.row.good.isSale === 0 ? '求购' : '出售' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="商品图片">
        <template slot-scope="scope">
          <img :src="backUrl + scope.row.imgUrl" alt="无" class="product-image">
        </template>
      </el-table-column>
      <el-table-column prop="good.goodName" label="商品名称"></el-table-column>
      <el-table-column prop="good.categoryId" label="商品类别">
        <template slot-scope="scope">
          {{ categoryMap[scope.row.good.categoryId] }}
        </template>
      </el-table-column>
      <el-table-column prop="good.description" label="商品简介"></el-table-column>
      <el-table-column prop="good.counts" label="商品数量"></el-table-column>
      <el-table-column prop="good.expressPrice" label="商品邮费"></el-table-column>
      <el-table-column prop="good.price" label="售价/求购价"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <button class="change" @click="handleEdit(scope.row)">修改</button>
          <button class="delete" @click="deleteRow(scope.row)">删除</button>
        </template>
      </el-table-column>
    </el-table>



    <el-dialog :visible.sync="ChangeDialogForm">
      <!--  修改表格-->

      <el-form :model="changeForm" title="修改商品">
        <el-form-item label="商品图片">
          <el-upload
              :disabled = "true"
              :action="uploadUrl"
              :show-file-list="false"
              :on-success="handleUploadImgSuccess2"
              :before-upload="beforeUpload">
            <img style="border-radius: 50%;margin-top: -2vh;margin-left: 2vw" height="80vh" width="80vh"
                 v-if="changeForm.imgUrl"
                 :src="backUrl+changeForm.imgUrl" class="imgUrl" alt="商品图片"/>
<!--            <img style="border-radius: 50%;margin-top: -2vh;margin-left: 2vw" height="80vh" width="80vh"-->
<!--                 v-for="(img,index) in changeImgList"-->
<!--                 :src="backUrl+img" class="imgUrl" alt="商品图片" :key="index"/>-->
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <!--    <input type="file" class="btn" accept="image/*" @change="handleFileUpload">-->
        <el-form-item label="商品名">
          <el-input :disabled = "true" v-model="changeForm.goodName" name="goodName"></el-input>
        </el-form-item>
        <el-form-item label="商品的属性">
          <input :disabled = "true" v-model="changeForm.isSale" type="radio" id="1" value="1" name="1"><label for="1">出售</label>
          <input :disabled = "true" v-model="changeForm.isSale" type="radio" id=0 value="0" name="0"><label for="0">求购</label>
        </el-form-item>
        <el-form-item label="商品类别">
          <el-input :disabled = "true" v-model="changeForm.categoryId" name="category"></el-input>
        </el-form-item>
        <el-form-item label="输入修改后的商品数量">
          <el-input v-model="changeForm.counts" name="counts"></el-input>
        </el-form-item>
        <el-form-item label="输入修改后的商品简介">
          <el-input v-model="changeForm.description" name="description"></el-input>
        </el-form-item>
        <el-form-item label="输入修改后的商品价格">
          <el-input v-model="changeForm.price" name="price"></el-input>
        </el-form-item>
        <el-form-item label="输入修改后的商品邮费">
          <el-input v-model="changeForm.expressPrice" name="expressPrice"></el-input>
        </el-form-item>
        <el-button @click="cancelChange">取 消</el-button>
        <el-button type="primary" @click="changeTable()">确 定</el-button>
      </el-form>
    </el-dialog>


  </div>
</template>

<script>
// import PersonDialogForm from "@/components/user/PersonDialogForm.vue";

export default {
  name: 'PersonChange',
  components: {},
  data() {
    return {
      selectedFile: null, // 保存用户选择的文件
      imageUrl: '',        // 保存上传后的图片链接
      changeRow: 0,
      ChangeDialogForm: false,
      changeImgList:[{}],
      saveLoadImgList:[],
      changeForm: {
        imgUrl: '',
        goodId: 0,
        //
        goodName: '',
        categoryId: 0,
        description: '',
        counts: 0,
        price: 0.00,
        expressPrice: 0.00,
        publisherId: 0,//发布者id 也就是当前用户的id
        isSale: 0,
        //
        createTime: '',
      },
      NewDialogForm: false,


      newForm: {
        imgUrl: '',
        goodId: 0,
        //
        goodName: '',
        categoryId: 0,
        description: '',
        counts: 0,
        price: 0.00,
        expressPrice: 0.00,
        publisherId: 0,//发布者id 也就是当前用户的id
        isSale: 0,
        //
        createTime: '',
      },

      fileList: [],
      isModalVisible: false,
      // addresses: [
      //   { name: '张三', address: '广州市天河区体育西路100号', phone: '13800138000' },
      //   { name: '李四', address: '深圳市南山区海德三道', phone: '13900139000' },
      //   { name: '王五', address: '上海市浦东新区陆家嘴', phone: '13700137000' }
      // ],

      message: {
        avatarUrl: require('../../assets/Userimg/apple.jpg'),
        name: '用户小明',
        gender: '女',
        school: '武汉大学',
        password: "123",
      },

      // tableData: [
      //   { sort: '求购', img: require('../../assets/img/upload/0cf95c37-2665-4894-bd42-5f8de06c6d94.jpg'),name:'华为手机', introduction:'XXXXXXX',price:111 },
      // ],
      selectedRow: null,
      dialogVisible: false,

      //后端新增的
      //编辑表存储用户信息
      editUserIng: {
        personId: 0,
        userName: '',
        sex: 0,
        account: '',
        avatarUrl: '',
        campusId: 1,
        campusName:'',
        createTime: '2023-07-03 00:15:53',
        isLocked: 0,
        password: '',
        registerDate: '2023-07-03 00:15:53',
      },
      //当前界面用户信息
      userIng: {
        personId: 0,
        userName: '',
        sex: 0,
        account: '',
        avatarUrl: '',
        campusId: 1,
        createTime: '2023-07-03 00:15:53',
        isLocked: 0,
        password: '',
        registerDate: '2023-07-03 00:15:53',
      },

      //当前用户的学校
      personCampus: '',
      //所有学校的列表
      campusList: [{
        campusId: 0,
        campusName: '',
        createTime: '',
      }],
      //显示出来的密码
      displayPassword: '',

      //编辑个人信息弹框的控制信号
      PersondialogVisible: false,
      //编辑个人信息的表格
      PersoneditForm: {
        avatarUrl: '',
        userName: '',
        sex: 0,
        campusId: 0,
        password: '',
      },
      //性别选择框
      optionsSex: [{
        value: 0,
        label: '女'
      }, {
        value: 1,
        label: '男'
      }],

      uploadUrl: 'http://localhost:8090/carousel/upload', //上传图片调用的接口
      backUrl: "http://localhost:8090/images/upload/", //回显的路径
      image: [{}],

      //图片table
      tableData: [],

      //地址 收件人
      addresses: [{
        shoppingAddressId: 0,
        personId: 0,
        shoppingName: '',
        shoppingPhone: 0,
        shippingAddress: '',
        createTime: '',
      }],
      newItemAddresses: [{
        shoppingAddressId: 0,
        personId: 0,
        shoppingName: '',
        shoppingPhone: 0,
        shippingAddress: '',
        createTime: '',
      }],

      //获取分类列表
      categoryList: [{
        categoryId:0,
        categoryName:'',
        categoryRank:0,
        createTime:'',
        isDeleted:0,
      }],
      categoryMap:{},

      lastGoodId:0,
    }
  },
  methods: {
    //实现上传图片
    handleUploadImgSuccess(response) {
      this.saveLoadImgList.push(response.data)
    },
    handleUploadImgSuccess2(response) {
      this.changeForm.imgUrl = response.data
    },
    //上传头像
    handleUploadSuccess(response) {
      // 处理图片上传成功回调
      // 获取上传成功后的图片地址，并赋值给表单数据
      this.editUserIng.avatarUrl = response.data
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
    //选择学校时更改对应的campusId
    handleCampusChange() {
      this.editUserIng.campusId = this.campusList[this.editUserIng.campusId - 1].campusId;
    },
    //选择类别时更改对应的categoryId
    handleCategoryChange(){
      const selectedCategory = this.categoryList.find(category => category.categoryName === this.newForm.categoryId);
      if (selectedCategory) {
        this.newForm.categoryId = selectedCategory.categoryId;
      }
    },
    handleFileUpload(event) {
      this.selectedFile = event.target.files[0]; // 获取用户选择的文件
      // 使用FileReader读取文件内容，获取其DataURL
      const reader = new FileReader();
      reader.onload = () => {
        this.imageUrl = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    },

    //收货地址传值
    showModal() {
      let item = JSON.parse(JSON.stringify(this.addresses));
      this.newItemAddresses = item
      this.isModalVisible = true
    },
    saveAddress() {
      // 验证输入框是否为空
      if (!this.validateAll()) {
        this.$message({message: '请输入完整收货地址', type: 'warning'});
        return
      }
      this.addresses = JSON.stringify(this.newItemAddresses)
      //后端操作
      this.$axios({
        url: this.$http + '/person-shopping-address/addOrUpdate',
        method: 'post',
        data: this.addresses,
        headers: {
          'Content-Type': 'application/json',
          token: localStorage.getItem("token")
        }
      }).then(res => res).then(res => {
        if (res.data.resultCode === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.loadAddresses();
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
      // 保存修改后的收货地址信息
      this.loadAddresses();
      // 关闭弹窗
      this.isModalVisible = false
    },
    cancelChanges() {
      // 取消修改，关闭弹窗
      this.isModalVisible = false
    },
    addAddress() {
      //新增创建日期
      let formattedCreateTime;
      const createTime = new Date();
      const year = createTime.getFullYear();
      const month = String(createTime.getMonth() + 1).padStart(2, '0');
      const day = String(createTime.getDate()).padStart(2, '0');
      const hours = String(createTime.getHours()).padStart(2, '0');
      const minutes = String(createTime.getMinutes()).padStart(2, '0');
      const seconds = String(createTime.getSeconds()).padStart(2, '0');
      formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      // this.addresses.createTime = formattedCreateTime;
      // 添加新地址
      // this.addresses.push({personId:this.userIng.personId, shoppingName: '', shippingAddress: '', shoppingPhone: 0,createTime: formattedCreateTime })
      this.newItemAddresses.push({
        personId: this.userIng.personId,
        shoppingName: "",
        shippingAddress: "",
        shoppingPhone: 0,
        createTime: formattedCreateTime
      })
    },
    deleteAddress(index) {
      // 删除指定下标的地址
      this.newItemAddresses.splice(index, 1)
    },
    validateName(name) {
      // 验证姓名是否为空
      return name !== ''
    },
    validateAddress(address) {
      // 验证地址是否为空
      return address !== ''
    },
    validatePhone(phone) {
      // 验证电话号码是否为空或格式是否正确
      if (phone) {
        return false
      }
      const pattern = /^1[0-9]{10}$/
      return pattern.test(phone)
    },
    validateAll() {
      // 遍历所有地址信息，检查每个输入框是否为空
      for (const address of this.addresses) {
        if (address.shoppingName === '' || address.shippingAddress === '' || address.phone) {
          return false
        }
      }
      return true
    },

    saveChanges() {
      // 验证输入框是否为空
      if (!this.validateAll()) {
        this.$message({
          message: '请输入完整收货地址',
          type: 'warning'
        });
        //alert('请输入完整的收货信息')
        return
      }
    },
    saveAllInputs() {
      this.$message({
        message: '收货信息保存成功',
        type: 'success'
      });
    },


    //个人信息修改弹窗
    closePersonDialog() {
      this.PersondialogVisible = false
      // this.resetForm()//重置数据
    },
    editMessage() {
      // 获取选中的行数据
      //const selectedRows = this.$refs.messageTable.selection;

      let item = JSON.parse(JSON.stringify(this.userIng));
      //另存用户信息
      this.editUserIng = item;

      // 将当前轮播图的信息赋值给表单数据
      // this.PersoneditForm = this.editUserIng;
      // 编辑轮播图
      this.PersondialogVisible = true;

      // 设置标志位为false
      // this.isAddingCarousel = false;
    },
    cancelEdit() {
      let item = JSON.parse(JSON.stringify(this.userIng));
      this.editUserIng = item

      this.PersondialogVisible = false
    },
    PersonsaveMessage() {
      let userIng = this.userIng
      userIng.avatarUrl = this.editUserIng.avatarUrl;
      userIng.userName = this.editUserIng.userName;
      userIng.sex = this.editUserIng.sex;
      userIng.campusId = this.editUserIng.campusId;
      userIng.password = this.displayPassword;
      //新增创建日期
      let formattedCreateTime;
      if (typeof this.userIng.createTime === 'string') {
        formattedCreateTime = userIng.registerDate; // 不执行转化，保持原格式
      } else {
        const createTime = new Date();
        const year = createTime.getFullYear();
        const month = String(createTime.getMonth() + 1).padStart(2, '0');
        const day = String(createTime.getDate()).padStart(2, '0');
        const hours = String(createTime.getHours()).padStart(2, '0');
        const minutes = String(createTime.getMinutes()).padStart(2, '0');
        const seconds = String(createTime.getSeconds()).padStart(2, '0');
        formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      }
      userIng.createTime = formattedCreateTime;
      //后端操作
      this.$axios({
        url: this.$http + '/person/addOrUpdate',
        method: 'post',
        data: userIng,
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.userIng = userIng;
          localStorage.setItem("person", JSON.stringify(res.person));
          this.$router.go(0)
          //记录下当前用户输入的密码 可以盗号hhhh 方便之后显示
          localStorage.setItem("passwordNoMD5", userIng.password);
          this.load()
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
      this.PersondialogVisible = false; // 关闭编辑对话框
    },
    // 商品信息编辑
    openAddDialog() {
      // 清空选中的数据，打开弹窗用于添加新数据;
      this.NewDialogForm = true;
    },
    deleteRow(row) {
      this.tableData.splice(row, 1);
      //后端操作
      const id = row.good.goodId;
      this.$axios({
        url: this.$http + '/good/delete?goodId=' + id,
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          });
          this.loadImg();
        } else {
          this.$message({
            message: '删除失败！',
            type: 'error'
          });
        }

      })

    },
    handleEdit(row) {
      // 在这里修改表格某一行的值
      // 将商品信息设置到changeForm对象中
      this.changeForm.goodId = row.good.goodId;
      this.changeForm.imgUrl = row.imgUrl;
      this.changeForm.goodName = row.good.goodName;
      this.changeForm.isSale = row.good.isSale;
      this.changeForm.categoryId = row.good.categoryId;
      this.changeForm.counts = row.good.counts;
      this.changeForm.description = row.good.description;
      this.changeForm.price = row.good.price;
      this.changeForm.expressPrice = row.good.expressPrice;

      this.ChangeDialogForm = true;

      // this.changeRow=row;
    },
    cancelChange() {
      this.ChangeDialogForm = false;
      this.changeForm = {};
    },
    changeTable() {
      if (this.changeForm.goodName !== "" && this.changeForm.description !== ""
          && this.changeForm.categoryId !== 0 && this.changeForm.counts !== 0) {
        //后端操作
        let formattedCreateTime;
        const createTime = new Date();
        const year = createTime.getFullYear();
        const month = String(createTime.getMonth() + 1).padStart(2, '0');
        const day = String(createTime.getDate()).padStart(2, '0');
        const hours = String(createTime.getHours()).padStart(2, '0');
        const minutes = String(createTime.getMinutes()).padStart(2, '0');
        const seconds = String(createTime.getSeconds()).padStart(2, '0');
        formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        this.changeForm.createTime = formattedCreateTime;

        var newRow = {
          good: {
            goodId: this.changeForm.goodId,
            isSale: this.changeForm.isSale,
            goodName: this.changeForm.goodName,
            categoryId: this.changeForm.categoryId,
            counts: this.changeForm.counts,
            price: this.changeForm.price,
            description: this.changeForm.description,
            publisherId: this.userIng.personId,
            expressPrice: this.changeForm.expressPrice,
            createTime: formattedCreateTime,
          },
          imgUrl:this.changeForm.imgUrl,
          // imgUrl: this.saveLoadImgList[0].imgUrl,
        };

        //后端操作
        this.$axios({
          url: this.$http + '/good/updateGoodAndImage',
          method: 'post',
          data: newRow,
          headers: {
            'Content-Type': 'application/json',
            'token': localStorage.getItem('token')
          }
        }).then(res => res.data).then(res => {
          if (res.resultCode === 200) {
            this.$message({
              message: '修改成功！',
              type: 'success'
            });
            this.load()
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        })
        this.ChangeDialogForm = false;
        // this.$message.success('修改成功');
        this.changeForm = {};
        this.CommentDialogVisible = false; // 关闭编辑对话框
      } else {
        this.$message.error('输入不能为空')
      }
    },
    cancelNew() {
      this.NewDialogForm = false;
      this.newForm = {};
    },
    addRow() {
      // 将选中的类别转换为汉字
      if (this.newForm.goodName === "" || this.newForm.description === "" || this.newForm.counts === 0) {
        this.$message.error('输入不能为空')
        this.$message.error('你在干什么？！')
      } else {
        //新增创建日期
        let formattedCreateTime;
        const createTime = new Date();
        const year = createTime.getFullYear();
        const month = String(createTime.getMonth() + 1).padStart(2, '0');
        const day = String(createTime.getDate()).padStart(2, '0');
        const hours = String(createTime.getHours()).padStart(2, '0');
        const minutes = String(createTime.getMinutes()).padStart(2, '0');
        const seconds = String(createTime.getSeconds()).padStart(2, '0');
        formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

        this.lastGoodId = this.changeForm.goodId;
        var newRow = {
          good: {
            goodId: this.changeForm.goodId,
            isSale: this.newForm.isSale,
            goodName: this.newForm.goodName,
            categoryId: this.newForm.categoryId,
            counts: this.newForm.counts,
            price: this.newForm.price,
            description: this.newForm.description,
            publisherId: this.userIng.personId,
            expressPrice: this.newForm.expressPrice,
            createTime: formattedCreateTime,
          },
          // imgUrl: this.newForm.imgUrl,
          imgUrl:this.saveLoadImgList[0]
        };
        this.image.push(newRow.imgUrl)
        //后端操作
        this.$axios({
          url: this.$http + '/good/addGoodAndImage',
          method: 'post',
          data: newRow,
          headers: {
            'Content-Type': 'application/json',
            'token': localStorage.getItem('token')
          }
        }).then(res => res.data).then(res => {

          if (res.resultCode === 200) {

            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            if(this.saveLoadImgList.length!==1){
              this.setImgList();
            }
            this.load()
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        })

        this.tableData.push(newRow);
        this.NewDialogForm = false;
        this.newForm = {};
      }
    },
    //向商品图片表中写入商品对应的所有图片
    setImgList(){
      //新增创建日期
      let formattedCreateTime;
      const createTime = new Date();
      const year = createTime.getFullYear();
      const month = String(createTime.getMonth() + 1).padStart(2, '0');
      const day = String(createTime.getDate()).padStart(2, '0');
      const hours = String(createTime.getHours()).padStart(2, '0');
      const minutes = String(createTime.getMinutes()).padStart(2, '0');
      const seconds = String(createTime.getSeconds()).padStart(2, '0');
      formattedCreateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

      let item = []
      for(let i = 1; i < this.saveLoadImgList.length; i++ ){
        // item.push(this.saveLoadImgList[i],this.changeForm.goodId,formattedCreateTime)
        const imgAndGood = {
          imgUrl: this.saveLoadImgList[i],
          goodId: this.lastGoodId,
          createTime: formattedCreateTime
        };

        item.push(imgAndGood);
      }

      this.$axios({
        url: this.$http + '/img-and-good/updateGoodAndImg',
        method: 'post',
        data: item,
        headers: {
          'Content-Type': 'application/json',
          'token': localStorage.getItem('token')
        }
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.$message({
            message: '新增所有商品图片成功！',
            type: 'success'
          });
          this.load()
        } else {
          this.$message({
            message: '新增所有商品图片失败！',
            type: 'error'
          });
        }
      })

    },
    openDialog() {
      this.dialogVisible = true;
    },
    closeDialog() {
      this.dialogVisible = false;
      this.form.sort = '';
      this.form.img = '';
      this.form.name = '';
      this.form.introduction = '';
      this.form.price = '';
    },
    //获取学校表
    loadCampus() {
      this.$axios({
        url: this.$http + '/near-sell/listCampuses',
        method: "get",
        headers: {
          token: localStorage.getItem("token")
        },
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.campusList = res.data
          // this.personCampus = this.campusList[this.userIng.campusId - 1].campusName
        } else {
          alert('获取数据失败')
        }
      })
    },
    //获取当前用户信息
    load() {
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
      userMessage.registerDate = formattedCreateTime;
      this.message = userMessage
      this.userIng = userMessage
      this.displayPassword = localStorage.getItem("passwordNoMD5");
      this.loadImg();
    },
    loadAddresses() {
      this.$axios({
        url: this.$http + '/person-shopping-address/getAddressesById?personId=' + this.userIng.personId,
        method: "get",
        headers: {
          token: localStorage.getItem("token")
        },
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.addresses = res.data
        } else {
          alert('获取数据失败')
        }
      })
    },
    //获取当前personId所对应的所有商品所对应的首张图片
    loadImg() {
      this.$axios({
        url: this.$http + '/good/getImg?personId=' + this.userIng.personId,
        method: "get",
        headers: {
          token: localStorage.getItem("token")
        },
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.tableData = res.data

        } else {
          alert('获取数据失败')
        }
      })
    },
    loadCategory() {
      this.$axios({
        url: this.$http + '/good-category/list',
        method: "get",
        headers: {
          token: localStorage.getItem("token")
        },
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.categoryList = res.data
          // 创建一个映射对象，用于查找 categoryId 对应的 categoryName
          this.categoryMap = {};
          for (const category of this.categoryList) {
            this.categoryMap[category.categoryId] = category.categoryName;
          }
        } else {
          alert('获取数据失败')
        }
      })
    },
    // eslint-disable-next-line no-unused-vars
    handleRemove(file,fileList)
    {
      this.saveLoadImgList.pop();
    },
  },
  beforeMount() {
    this.load();
    this.loadImg();
    this.loadCampus();
    this.loadAddresses();
    this.loadCategory();
  },
  computed: {
    avatarPath() {
      if (this.userIng.avatarUrl !== '') {
        return this.backUrl + this.userIng.avatarUrl;
      } else {
        return require("../../assets/Userimg/apple.jpg");
      }
    },
    formattedCounts: {
      get() {
        if (this.newForm.counts === 0) {
          return '';
        } else {
          return this.newForm.counts;
        }
      },
      set(value) {
        // 处理输入框值的变化，更新 newForm.counts
        if (value === '') {
          this.newForm.counts = 0;
        } else {
          this.newForm.counts = parseInt(value, 10);
        }
      }
    },
  }
}

</script>


<style scoped>
input[type="radio"] + label, input[type="checkbox"] + label {

  background: linear-gradient(#bddbce 10%, #8cc0aa 50%, #bddbce);
  border-radius: 10px;
  padding: 0 10px;
  margin-left: 15px;
  border: solid 2px darkseagreen;
  color: white;
}

input[type="radio"]:checked + label, input[type="checkbox"]:checked + label {
  color: white;
  background: linear-gradient(#48846a 10%, #6bae91 50%, #48846a);
}

input[type="radio"], input[type="checkbox"] {
  display: none;
}

.square {
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
  color: white;
}

.square p {
  color: white;
  font-size: 16px;
  font-weight: bold;
  margin: 0;
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

.change {
  background-color: #b3d1b1;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 10px;
  margin-right: 10px;
}

.change:hover {
  background-color: #809e7e;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 10px;
  margin-right: 10px;
}

.delete {
  background-color: #b4b4a4;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 10px;
  margin-right: 10px;
}

.delete:hover {
  background-color: #909083;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 10px;
  margin-right: 10px;
}

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

.round-image {
  border-radius: 50%;
}


.modal {
  position: fixed;
  z-index: 100;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  overflow-y: scroll;
  height: 35vw;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

input[type="text"], input[type="tel"], textarea {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #ccc;
  border-radius: 4px;
  resize: vertical;
}

.input-group {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.input-group > label {
  margin-right: 10px;
  flex-basis: 50%;
}

.product-image {
  width: 8vw;
  height: 8vw;
}


</style>