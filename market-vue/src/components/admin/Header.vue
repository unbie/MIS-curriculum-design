<template>
  <div style="display:flex;line-height: 6vh;">
    <div><i :class="icon" style="font-size:3vb;padding-top: 1.6vh" @click="collapse"></i> </div>
    <div style="flex: 1;text-align: center;font-size: 3vb"><span id="welcome">欢迎来到管理员界面</span></div>
   <span style="font-size: 2.5vb">{{ adminUsername }}</span>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      adminUsername:'',
    }
  },
  props:{
    icon:String
  },
  methods:{
    collapse(){
      this.$emit('doCollapse')

    }
  },
  computed:{
  },
  mounted() {
    this.adminUsername = JSON.parse(localStorage.getItem('admin')).adminUsername
  }
}


</script>

<style scoped>
.el-header{background-color: #21675e;}
.el-dropdown {
  display: inline-block;
  position: relative;
  color: #606266;
  font-size: 14px;
  text-align: center;
}
div{color:white;font-family: 华文宋体}
</style>