<template>
  <div>
    <button @click="showModal">修改收货地址</button>
    <div v-if="isModalVisible" class="modal">
      <div class="modal-content">
        <h2>编辑收货信息</h2>
        <div v-for="(address, index) in addresses" :key="index">
          <label for="name">姓名：</label>
          <input type="text" id="name" v-model="address.name"><br>
          <label for="address">地址：</label>
          <textarea id="address" rows="3" v-model="address.address"></textarea><br>
          <label for="phone">电话：</label>
          <input type="tel" id="phone" v-model="address.phone"><br>
          <button @click="deleteAddress(index)">删除</button>
        </div>
        <button @click="addAddress">添加新地址</button>
        <div class="modal-footer">
          <button @click="saveChanges">保存</button>
          <button @click="cancelChanges">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:'InfoTest',
  data() {
    return {
      isModalVisible: false,
      addresses: [
        { name: '张三', address: '广州市天河区体育西路100号', phone: '13800138000' },
        { name: '李四', address: '深圳市南山区海德三道', phone: '13900139000' },
        { name: '王五', address: '上海市浦东新区陆家嘴', phone: '13700137000' }
      ]
    }
  },
  methods: {
    showModal() {
      this.isModalVisible = true
    },
    saveChanges() {
      // 保存修改后的收货地址信息
      // 关闭弹窗
      this.isModalVisible = false
    },
    cancelChanges() {
      // 取消修改，关闭弹窗
      this.isModalVisible = false
    },
    addAddress() {
      // 添加新地址
      this.addresses.push({ name: '', address: '', phone: '' })
    },
    deleteAddress(index) {
      // 删除指定下标的地址
      this.addresses.splice(index, 1)
    }
  }
}
</script>

<style>
.modal {
  position: fixed;
  z-index: 1;
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
</style>
