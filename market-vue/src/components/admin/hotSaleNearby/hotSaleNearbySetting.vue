<template>
  <div>
    <el-card>
      <div slot="header">
        <h2>附近热卖配置</h2>
      </div>
      <div class="config-header">
        <div class="config-buttons">
          <el-button type="primary" icon="el-icon-circle-plus" @click="addCampus">新增</el-button>
          <el-button type="success" icon="el-icon-edit" @click="edit" :disabled="!selectedCampus">编辑</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteCampus" :disabled="!selectedCampus">删除</el-button>
        </div>
        <el-form class="config-form">
          <el-form-item >
            <!-- 学校选择器 -->
            <el-select v-model="selectedCampus" placeholder="请选择学校">
              <!-- 默认选项 -->
              <el-option label="请选择学校" value=""></el-option>
              <!-- 学校选项 -->
              <el-option
                  v-for="campus in campuses"
                  :key="campus.campusId"
                  :label="campus.campusName"
                  :value="campus.campusId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <br />
    <!-- 商品列表 -->
    <el-table :data="pagedGoodsList" :border="true" v-if="selectedCampus" ref="goodsLiFstTable" height="500">
      <el-table-column label="商品图片">
        <template slot-scope="scope">
          <img :src="backUrl+scope.row.goodImageUrl" alt="商品图片" style="width: 100px; height: auto;" />
        </template>
      </el-table-column>
      <el-table-column prop="goodName" label="商品名称"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
    </el-table>

    <el-button v-else disabled>请选择学校</el-button>

    <el-pagination
        :current-page="currentPageList"
        :page-sizes="[2, 5, 10]"
        :page-size="pageSizeList"
        :total="filteredGoodsList.length"
        layout="sizes, prev, pager, next, jumper"
        @size-change="handlePageSizeChangeList"
        @current-change="handlePageChangeList"
    ></el-pagination>

    <!--    附近热卖配置  -->
    <el-dialog title="商品配置" :visible.sync="dialogVisible" :append-to-body="true">
      <h3>{{ selectedCampusName }}</h3>
      <el-table :data="pagedGoods" :border="true" ref="goodsTable" height="500"
                @selection-change="handleGoodsSelectionChange" :row-key="row => row.goodId">
        <el-table-column type="selection" width="50" v-model="selecteds"></el-table-column>
        <el-table-column prop="goodName" label="商品名称"></el-table-column>
        <el-table-column prop="image" label="商品图片">
          <template slot-scope="scope">
            <img :src="backUrl+scope.row.goodImageUrl" alt="商品图片" style="width: 100px; height: 100px;">
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
          :current-page="currentPage"
          :page-sizes="[2, 5, 10]"
          :page-size="pageSize"
          :total="filteredGoods.length"
          layout="sizes, prev, pager, next, jumper"
          @size-change="handlePageSizeChange"
          @current-change="handlePageChange"
      ></el-pagination>

      <!-- 确认和取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveGoods">确认</el-button>
      </span>
    </el-dialog>

    <!-- 新增学校对话框 -->
    <el-dialog :visible.sync="addCampusDialogVisible" title="新增学校">
      <!-- 新增学校表单 -->
      <el-form ref="addCampusForm" :model="newCampus" :rules="addCampusFormRules" label-width="80px">
        <!-- 学校名称输入框 -->
        <el-form-item label="学校名称" prop="campusName">
          <el-input v-model="newCampus.campusName"></el-input>
        </el-form-item>
      </el-form>
      <!-- 确认和取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancelAddCampus">取消</el-button>
        <el-button type="primary" @click="handleAddCampus">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'hotSaleNearbySetting',
  data() {
    return {
      selectedCampus: '', // 当前选中的学校ID
      campuses: [], // 学校列表
      goodsList: [], // 对应学校已配置的商品列表
      goods: [], //对应学校所有的商品
      dialogVisible: false, // 编辑对话框是否可见
      editedGood: {}, // 当前编辑的商品对象
      editFormRules: {},// 编辑表单校验规则

      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量

      currentPageList: 1, // 当前页数
      pageSizeList: 10, // 每页显示的数量

      addCampusDialogVisible: false, // 新增学校对话框是否可见
      newCampus: {campusName: ''}, // 新增学校的数据
      addCampusFormRules: { // 新增学校表单的校验规则
        campusName: [
          {required: true, message: '学校名称不能为空', trigger: 'blur'}
        ]
      },

      selectedGoods: [],// 选中的商品
      selectedItems: [], // 存储所有选中表项
      maxSelectedGoods: 8, // 最大选择商品数量
      selecteds: [],

      backUrl: this.$backUrl

    };
  },
  computed: {
    filteredGoodsList() {
      // 根据选择的学校ID过滤商品列表
      return this.goodsList.filter(good => good.campusId === this.selectedCampus);
    },
    filteredGoods() {
      // 根据选择的学校ID过滤所有商品列表
      return this.goods.filter(good => good.campusId === this.selectedCampus);
    },
    pagedGoodsList() {
      const start = (this.currentPageList - 1) * this.pageSizeList;
      const end = start + this.pageSizeList;
      return this.filteredGoodsList.slice(start, end);
    },
    pagedGoods() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredGoods.slice(start, end);
    },
    selectedCampusName() {
      const selected = this.campuses.find(campus => campus.campusId === this.selectedCampus);
      if (selected) {
        this.selectedGoods = this.goodsList.filter(good => good.campusId === selected.campusId);
        return selected.campusName;
      }
      return '';
    },
    //一页中未选中的数据
    unselecteds() {
      // 通过筛选源数据和选中项数组，获取未选中的表项
      return this.pagedGoods.filter(item => !this.selecteds.includes(item));
    }
  },
  watch: {
    selectedCampus(newVal) {
      // 在选择学校时执行操作
      this.selectedItems = [];
    },
  },
  methods: {
    // 获取学校列表
    fetchCampuses() {
      // 模拟请求数据
      // this.campuses = [
      //   { campusId: 1, campusName: '武汉大学' },
      // ];
      this.$axios({
        url: this.$http + '/near-sell/listCampuses',
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        if (res.resultCode === 200) {
          this.campuses = res.data
        } else {
          alert('获取数据失败')
        }
      })
    },
    // 获取已配置的商品列表
    fetchGoodsList() {
      this.$axios({
        url: this.$http + '/near-sell/listCampusGoodsConfigured',
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        // // console.log(res.data)
        if (res.resultCode === 200) {
          this.goodsList = res.data
        } else {
          alert('获取数据失败')
        }
      })
      // 模拟请求数据
      // this.goodsList = [
      //   { campusId: 1, goodId:1, goodName: '商品1', goodImageUrl: require('../../../../../market/src/main/resources/static/images/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg') ,createTime: "2006-12-01 00:00:00"},
      //  ];

    },
    //获取所有的商品列表
    fetchGoods() {
      this.$axios({
        url: this.$http + '/near-sell/listCampusGoods',
        method: 'get',
        headers: {
          token: localStorage.getItem("token")
        }
      }).then(res => res.data).then(res => {
        // // console.log(res.data)
        if (res.resultCode === 200) {
          this.goods = res.data
        } else {
          alert('获取数据失败')
        }
      })
      // 模拟请求数据
      // this.goods = [
      //   { campusId: 1, goodId:1, goodName: '商品1', goodImageUrl: require('../../../../../market/src/main/resources/static/images/upload/0a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg') ,createTime:'2006-12-01 00:00:00'},
      //   { campusId: 2, goodId:9, goodName: '商品3', goodImageUrl: require('../../../assets/img/upload/0ae89667-8a69-4efc-b8d8-c0ebaf56753a.jpg')  ,createTime:'2006-12-01 00:00:00' },
      // ];
    },

    //新增
    //清空newCampus
    resetNew() {
      this.newCampus = {campusName: ""}
    },
    // 新增学校按钮点击事件
    addCampus() {
      this.resetNew();
      this.addCampusDialogVisible = true; // 打开新增学校对话框
    },

    // 确认新增学校按钮点击事件
    handleAddCampus() {
      this.$refs.addCampusForm.validate((valid) => {
        if (valid) {
          // 判断新增学校名称是否与已有学校名称重复
          const isDuplicate = this.campuses.some(
              (campus) =>
                  campus.campusName === this.newCampus.campusName.trim()
          );
          if (isDuplicate) {
            this.$message.error('学校名称重复，请输入不同的学校名称');
          } else {
            // 获取当前 campuses 中最大的 id 值
            const maxId = Math.max(
                ...this.campuses.map((campus) => campus.campusId)
            );
            let newId = 1;
            if (maxId !== -Infinity) {
              newId = maxId + 1
            }
            const formattedDate = this.$moment().format('YYYY-MM-DD HH:mm:ss');
            // 创建一个新学校
            const newCampus = {
              campusId: newId,
              campusName: this.newCampus.campusName.trim(),
              createTime: formattedDate,
            };

            // 将新增学校的数据添加到学校列表中
            this.campuses.push({
              campusId: newId,
              campusName: newCampus.campusName,
            });

            // 新增学校跨域请求
            this.$axios({
              url: this.$http + '/near-sell/addCampus',
              data: newCampus,
              method: 'post',
              headers: {
                token: localStorage.getItem("token")
              }
            }).then(res => res.data).then(res => {
              if (res.resultCode === 200) {
                this.fetchCampuses()
              } else {
                alert('更新数据失败')
              }
            })
            this.$message.success('新增成功');
            this.addCampusDialogVisible = false; // 关闭新增学校对话框
          }
        } else {
          this.$message.error('请输入学校名称'); // 表单项验证不通过，给出错误提示
        }
      });
    },
    // 取消新增学校按钮点击事件
    handleCancelAddCampus() {
      this.addCampusDialogVisible = false; // 关闭新增学校对话框
    },
    deleteCampus() {
      // 弹出确认对话框，确认后发送删除学校的请求
      this.$confirm('确定要删除该学校吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 根据选中的学校ID找到对应的学校对象，并使用splice方法将其从campuses数组中删除
        const index = this.campuses.findIndex(campus => campus.campusId === this.selectedCampus);
        if (index > -1) {
          this.campuses.splice(index, 1);
          //后端执行删除操作
          this.$axios({
            url: this.$http + '/near-sell/deleteCampus?campusId=' + this.selectedCampus,
            method: 'get',
            headers: {
              token: localStorage.getItem("token")
            }
          }).then(res => res.data).then(res => {
            if (res.resultCode === 200) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.fetchCampuses()
            } else {
              this.$message.error("删除失败")
            }
          })

          this.selectedCampus = ''; // 重置选中的学校ID
        }

      }).catch(() => {
        // 取消删除操作
        this.$message.info('已取消删除');
      });
    },
    // 编辑商品
    edit() {
      // 打开编辑对话框，并将选中商品的数据赋值给this.editedGood
      this.dialogVisible = true;
      //打开编辑时，将所有商品页面列表的商品页号和页大小初始化
      this.pageSize = 10
      this.currentPage = 1
      //默认选中已经配置好的
      this.selectedDefault();

    },
    //默认选中
    selectedDefault() {
      this.$nextTick(() => {
        this.pagedGoods.forEach(item => {
          const isSelected = this.selectedGoods.some(selectedItem => selectedItem.goodId === item.goodId);
          if (isSelected) {
            this.$refs.goodsTable.toggleRowSelection(item, true);
          }
        })
      });

    },
    // 保存商品
    saveGoods() {
      // 判断选中的商品数量是否大于九
      if (this.selectedItems.length > this.maxSelectedGoods) {
        const message = `对不起，您只能选择${this.maxSelectedGoods}个商品，而现在有${this.selectedItems.length}个商品`;
        this.$message.error(message);
      } else {
        //???????这个地方逻辑没看懂
        const selectedCampusId = this.selectedCampus;
        const newGoodsList = this.goodsList.filter(good => good.campusId !== selectedCampusId);
        newGoodsList.push(...this.selectedItems);
        // 从 newGoodsList 中筛选属于特定学校的配置商品，并封装成 JSON 数据格式
        const campus = this.campuses.find(c => c.campusId === selectedCampusId);
        // 创建 campusGoods 对象来存储学校的配置商品信息
        const campusGoods = {
          campusId: campus.campusId,         // 学校ID
          campusName: campus.campusName,     // 学校名称
          createTime: campus.createTime // 创建时间，此处为固定值
        };

        // 使用循环遍历 newGoodsList，将属于特定学校的商品ID存储到 campusGoods 对象中
        for (let i = 0; i < 8; i++) {
          const good = newGoodsList.find(g => g.campusId === selectedCampusId);
          if (good) {
            campusGoods[`goodId${i + 1}`] = good.goodId;  // 将商品ID存储到对应属性中
            newGoodsList.splice(newGoodsList.indexOf(good), 1); // 从 newGoodsList 中移除已处理的商品
          } else {
            campusGoods[`goodId${i + 1}`] = null;  // 如果 newGoodsList 中没有足够的商品，则将对应属性设置为 null
          }
        }

        // 之后执行跨域更新 （先删后增 避免顺序出错）
        this.$axios({
          url: this.$http + '/near-sell/deleteCampus?campusId=' + this.selectedCampus,
          method: 'get',
          headers: {
            token: localStorage.getItem("token")
          }
        }).then(res => res.data).then(res => {
          if (res.resultCode === 200) {
            this.fetchCampuses()
          } else {
            this.$message.error("操作异常")
          }
        })

        this.$axios({
          url: this.$http + '/near-sell/saveConfigured',
          data: campusGoods,
          method: 'post',
          headers: {
            token: localStorage.getItem("token")
          }
        }).then(res => res.data).then(res => {
          if (res.resultCode === 200) {
            this.fetchGoodsList()
          } else {
            alert('更新数据失败')
          }
        })

        this.goodsList = newGoodsList;
        this.dialogVisible = false;
      }
    },

    // 在页码变化时更新当前页数，并触发计算属性pagedGoodsList的重新计算
    handlePageChange(currentPage) {

      // 更新当前页码
      this.currentPage = currentPage;

      //删除selectedGoods中重复的项
      this.selectedGoods = Array.from(new Set(this.selectedItems))
      //每页数量变化时，还是能够显示默认勾选
      this.selectedDefault();
    },

    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize;
      // 当项数改变时，重置当前页为第一页
      this.currentPage = 1;

      //每页数量变化时，还是能够显示默认勾选
      this.selectedDefault();
    },

    handlePageChangeList(currentPageList) {
      this.currentPageList = currentPageList;
    },

    handlePageSizeChangeList(pageSizeList) {
      this.pageSizeList = pageSizeList;
      // 当项数改变时，重置当前页为第一页
      this.currentPageList = 1;
    },

    handleGoodsSelectionChange(selection) {
      // selection 为当前页选中的表项数组
      this.selecteds = selection
      const selectedItemsToAdd = selection.filter(item => !this.selectedItems.includes(item));
      // 添加选中的表项到 selectedItems 数组
      this.selectedItems.push(...selectedItemsToAdd);

      // 从 selectedItems 数组中删除未选中的表项
      for (const item of this.unselecteds) {
        const index = this.selectedItems.indexOf(item);
        if (index > -1) {
          this.selectedItems.splice(index, 1);
        }
      }
      //将selectedGoods同步更新
      this.selectedGoods.push(...selectedItemsToAdd)
    },


  },
  created() {
    // 在组件创建时获取学校列表和已配置商品列表数据和所有商品列表
    this.fetchCampuses();
    this.fetchGoodsList();
    this.fetchGoods();
  },
};
</script>

<style scoped>
.config-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.config-buttons {
  margin-bottom: 10px;
}

.config-form {
  flex-grow: 1;
  margin-top: 8px;
  margin-left: 20px;
}
</style>