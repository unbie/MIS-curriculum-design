<template>
  <div>
    <el-container style="height: 100vh; border: 0px solid #eee">
      <el-aside width="aside_width" style="background-color:#2D8578;height: 100vh;overflow: visible">
        <Aside :isCollapse="isCollapse"></Aside>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 2vb">
          <Header @doCollapse="doCollapse" :icon = "icon"></Header>
        </el-header>

        <el-main>
<!--          <Main></Main>-->
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/admin/Aside.vue";
import Header from "@/components/admin/Header.vue";


export default {
  components: {Header, Aside},
  data(){
    return{
      isCollapse:false,
      aside_width:'5vw',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse()
    {
      this.isCollapse=!this.isCollapse
      if(!this.isCollapse){
        this.aside_width='5vw'
        this.icon='el-icon-s-fold'
      }
      else{ this.aside_width='1vw'
        this.icon='el-icon-s-unfold'}
    },
  }
}
</script>

<style>
.el-header {
  background-color: #21675e;
  color: #333;
  line-height: 6vh;
  height: 8vh !important;
}
.el-container{
  background-color: white;
}
body{
  margin: 0;
}

</style>