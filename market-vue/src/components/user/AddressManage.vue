<template>
<div>
  <div style=" position: absolute;top:5%;left: 45%">
    <p style="color: #2b5b55;font-size: 20px">收货地址管理</p>
  </div>
  <div>
    <button style="position: absolute;top:8%;left: 2%">添加地址</button>
  </div>

  <div style="position: absolute;top:15%;left: 12%">
    <p>收件人：某某某</p>
    <p>手机号：12346571243</p>
    <p>收货地址：某某省某某市某某地区某某街道某某小区</p>
    <el-button style="position: absolute;top:40%;left: 120%" type="goon" icon="el-icon-edit" circle></el-button>
  </div>

  <div style="position: absolute;top:35%;left: 12%">
    <p>收件人：某某某</p>
    <p>手机号：12346571243</p>
    <p>收货地址：某某省某某市某某地区某某街道某某小区</p>
    <el-button style="position: absolute;top:40%;left: 120%" type="goon" icon="el-icon-edit" circle></el-button>
  </div>


</div>
</template>
<script>
export default {
  name: 'AddressManage',
  data(){
    return{

    }
  },
  methods:{


  }
}


</script>

<style scoped>
.el-button--goon.is-active,
.el-button--goon:active {
  background: #20B2AA;
  border-color: #20B2AA;
  color: #fff;
}
.el-button--goon:focus{
  color: #FFF;
  background-color: #20B2AA;
  border-color: #20B2AA;
}
.el-button--goon:hover {
  background: #48D1CC;
  border-color: #48D1CC;
  color: #fff;
}

.el-button--goon {
  color: #FFF;
  background-color: #20B2AA;
  border-color: #20B2AA;
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



</style>