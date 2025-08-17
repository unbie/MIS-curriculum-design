
<template>

  <div v-if="visible" class="dialog-container">

<div class="dialog-box">
    <button class="circle-btn" style="position: absolute; top: 17%; left: 25%;">+</button>
    <div class="square" style="position: absolute; top: 40%; left: 10%;">
      <p>商品名称</p>
    </div>
    <div class="square" style="position: absolute; top: 50%; left: 10%;">
      <p>价格</p>
    </div>
    <div class="square" style="position: absolute; top: 60%; left: 10%;">
      <p>商品描述</p>
    </div>
    <div class="square" style="position: absolute; top: 73%; left: 10%;">
      <p>类别</p>
    </div>
    <form action="" method="post" name="myForm" style="position: absolute; top: 41%; left: 28%;">
      <input v-model="form.name" type="text" name="name" maxlength="30" size="30"/>
    </form>
    <form action="" method="post" name="myForm" style="position: absolute; top: 51%; left: 28%;">
      <input v-model="form.price" type="text" name="price" maxlength="30" size="30"/>
    </form>

    <form style="position: absolute; top: 75%; left: 26%;">
      <input v-model="form.sort" type="radio" id="sell" value="sell" name="sort"><label for="sell">出售</label>
      <input v-model="form.sort" type="radio" id="buy" value="buy" name="sort"><label for="buy">求购</label>
    </form>

    <form style="position: absolute; top: 60%; left: 28%;">
       <textarea v-model="form.introduction" cols="50" rows="3">
       </textarea>
    </form>
    <button @click="handleSubmit" style="position: absolute; top: 85%; left: 60%;">确认</button>
    <button @click="handleCancel" style="position: absolute; top: 85%; left: 75%;">取消</button>
</div>




  </div>

</template>
<script>
export default {

  name:'PersonDialogForm',
  props: {
    rowData: {
      type: Object,
      required: true
    },
    visible: {
      type: Boolean,
      required: true
    },
  },
  data() {

    return {

      form: {
        sort:'',
        img:'',
        name:'',
        introduction:'',
        price:''
      }
    }
  },
  watch:{
    rowData: {
      immediate: true,
      handler(newRowData) {
        this.form = { ...newRowData }; // 将父组件传递的行数据复制到表单数据中
      }
    }
  },
  methods: {
    handleSubmit() {
      this.$emit('submit', { ...this.form });
      const newData = { ...this.tableData[this.editIndex], ...this.form };
      this.$set(this.tableData, this.editIndex, newData);
      this.dialogVisible = false;
    },
    handleCancel() {
      this.$emit('cancel');
    },



  },

};



</script>


<style scoped>
.dialog-container {
  position: fixed;
  z-index: 9999; /* 将弹窗放置在主界面上方 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5); /* 添加半透明背景 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.dialog-box {
  position: absolute;
  z-index: 10000; /* 将弹窗框放置在弹窗上方 */
  top: 20%;
  left: 30%;
  width: 40%;
  height: 60%;
  border: 1px solid #ccc; /* 添加边框 */
  background-color: white;
  padding: 20px;
}
.square {
  width: 75px;
  height: 35px;
  border-radius: 10px;
  background-color: #b3d1b1;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  color: black;
}
.square p {
  color: white;
  font-size: 12px;
  font-weight: bold;
  margin: 0;
}
label {
//display: block;
  margin-bottom: 10px;
}
.form {
  width: 500px;
  height: 400px;
  background-color: pink;
  margin: 0 auto;
  border-radius: 20px;
  padding-left: 50px;
}

.form h1 {
  text-align: center;
  padding-top: 50px;
}

input[type="radio"] ,input[type="checkbox"]{
  display: none;
}

input[type="radio"]+label ,input[type="checkbox"]+label{

  background: linear-gradient(#bddbce 10%,#8cc0aa 50%,#bddbce);
  border-radius: 10px;padding: 0 10px;margin-left: 15px;
  border:solid 2px darkseagreen;
  color:white;
}

input[type="radio"]:checked+label ,input[type="checkbox"]:checked+label{
  color:white;
  background: linear-gradient(#48846a 10%,#6bae91 50%,#48846a);
}

button {
  border-radius: 10px;
  background-color: #2D8578;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
button:hover {
  background-color: #2b5b55;
}
.circle-btn {
  border-radius: 50%;
  width: 50px;
  height: 50px;
  background-color: #add2c2;
  color: white;
  font-size: 20px;
}

</style>