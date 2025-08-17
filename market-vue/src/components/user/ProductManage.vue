<template>
<div>
  <button class="circle-btn" style="position: absolute; top: 17%; left: 25%;">+</button>
  <img src="../../assets/UserImg/default.jpeg" width="280" height="280" />
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
  <form action="" method="post" name="myForm" style="position: absolute; top: 42%; left: 20%;">
    <input type="text" name="age" maxlength="50" size="50"/>
  </form>
  <form action="" method="post" name="myForm" style="position: absolute; top: 52%; left: 20%;">
    <input type="text" name="age" maxlength="50" size="50"/>
  </form>
  <form style="position: absolute; top: 75%; left: 20%;">

    <input type="radio" id="sell" name="sort"><label for="sell">出售</label>
    <input type="radio" id="buy" name="sort"><label for="buy">求购</label>
  </form>
  <form style="position: absolute; top: 60%; left: 20%;">
       <textarea cols="100" rows="5">
       </textarea>
  </form>
  <button style="position: absolute; top: 85%; left: 75%;">确认</button>
  <button style="position: absolute; top: 85%; left: 82%;">取消</button>


</div>
</template>

<script>
export default {
  name: 'ProductManage',
  data(){
    return{

    }
  },
  methods:{

  }
}


</script>


<style scoped>
.square {
  width: 100px;
  height: 50px;
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
  font-size: 16px;
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