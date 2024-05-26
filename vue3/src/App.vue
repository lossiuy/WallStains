<template>
    <div id="app">
    <div id="title">
    同济大学幕墙污渍面积识别系统
    </div>
        <el-row>
            <el-col :span="4">
                <el-menu default-active="1" class="el-menu-vertical-demo" @select="handleMenuSelect">
                    <el-menu-item index="1">上传图片</el-menu-item>
                    <!-- <el-menu-item index="2">历史图片</el-menu-item> -->
                </el-menu>
            </el-col>
            <el-col :span="20">
                <router-view></router-view>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: 'App',
    methods: {
        handleMenuSelect(index) {
            switch (index) {
                case '1':
                    this.$router.push('/');
                    break;
                // case '2':
                //     this.$router.push('/other');
                //     break;
                default:
                    break;
            }
        }
    },
};
</script>

<style>
.el-row {
    margin: 20px 0;
}
#title {
    font-family: Arial, sans-serif; /* 设置字体 */
    font-size: 36px; /* 设置字体大小 */
    font-weight: bold; /* 设置字体粗细 */
    color: #0567b8; /* 设置文本颜色 */
  
    padding: 20px 0; /* 设置上下间距 */
    border-bottom: 2px solid #ccc; /* 添加底部边框 */
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
}
</style>