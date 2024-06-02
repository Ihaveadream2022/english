<template>
    <el-cascader ref="cs" v-model="name" filterable @change="onSelect" :props="props"></el-cascader>
</template>
<script>
    // 弹窗都在网页底部
    import { areasList } from "@/api/request";
    export default {
        name: "AreaCascader",
        props: {
            // data用于更新
            updateData: {
                type: Object,
                default: function () {
                    return {};
                },
            },
        },
        data() {
            return {
                name: null,
                visible: true,
                props: {
                    lazy: true,
                    // 默认执行一次, 每次点击下拉选项时也会执行
                    lazyLoad(node, resolve) {
                        console.log("current node:", node);
                        const { level } = node; // 下拉选项信息
                        let code = level > 0 ? node.data.value : 100000;
                        areasList({ parentId: code }).then((res) => {
                            const nodes = res.data.map((item) => ({
                                value: item.areaCode,
                                label: item.name,
                                leaf: level >= 2, // 从市开始算子叶级节点
                            }));
                            resolve(nodes);
                        });
                    },
                },
            };
        },
        mounted: function () {
            console.log("this.updateData: ", this.updateData);
            if (this.updateData.id) {
                this.onUpdate();
            }
        },
        methods: {
            // 选中节点时触发
            onSelect() {
                let seletNode = this.$refs.cs.getCheckedNodes()[0];
                if (seletNode) {
                    let data = [
                        { areaCode: seletNode.parent.parent.value, name: seletNode.parent.parent.label },
                        { areaCode: seletNode.parent.value, name: seletNode.parent.label },
                        { areaCode: seletNode.value, name: seletNode.label },
                    ];
                    this.$emit("HandleOnSelect", data);
                }
            },
            onUpdate() {
                this.name = [Number(this.updateData.provinceCode), Number(this.updateData.cityCode), Number(this.updateData.areaCode)];
            },
        },
    };
</script>
