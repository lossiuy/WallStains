<template>
    <el-table :data="tableData" :border="parentBorder" style="width: 100%">
        <el-table-column type="expand">
            <template #default="props">
                <div m="4">
                    <p m="t-0 b-2">所分块数: {{ props.row.num }}</p>
                    <p m="t-0 b-2">污渍面积: {{ props.row.result }}</p>
                    <p m="t-0 b-2">检测时间: {{ props.row.time }}</p>
                </div>
            </template>
        </el-table-column>
        <el-table-column label="图片名称" prop="name" />
        <el-table-column label="图片展示">
            <template #default="scope">
                    <el-image style="width: 400px; height: 200px" :src="scope.row.photo" :fit="cover"></el-image>
            </template>
        </el-table-column>
    </el-table>
    
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const parentBorder = ref(true);
const tableData = ref([]);

onMounted(() => {
    axios.get('http://localhost:8080/historydata')
        .then(response => {
            tableData.value = response.data;
        })
        .catch(error => {
            console.error('Error fetching tableData:', error);
        });
});
// const parentBorder = ref(true);
// const tableData = [
//     {
//         name: "1.jpg",
//         photo: "/src/assets/1.jpg", // 修改成图片的相对路径
//         num: "12",
//         result: "200平方厘米",
//         time: "2024-4-26",
//     },
//     {
//         name: "2.jpg",
//         photo: "/src/assets/1.jpg", // 修改成图片的相对路径
//         num: "12",
//         result: "200平方厘米",
//         time: "2024-4-29",
//     },
// ];
</script>
