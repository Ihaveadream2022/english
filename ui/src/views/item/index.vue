<template>
    <div>
        <div class="title">
            <h4>Item Management</h4>
        </div>
        <!-- Audio Tag -->
        <audio ref="audio" style="display: none"></audio>
        <audio ref="audioList" style="display: none"></audio>
        <!-- Search Area -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="table.searchForm" ref="searchForm" class="demo-form-inline" label-width="100px" size="mini">
                <div class="inpBox">
                    <el-form-item label="Keyword :" prop="keyword">
                        <el-input placeholder="keyword" v-model="table.searchForm.keyword" @keyup.enter.native="handleEnter"></el-input>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item>
                        <el-button type="primary" @click="tableSearch">Query</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="tableResetForm">Reset</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="editDialogOpen">Create</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="itemGenerate">Generate JSON Files</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="speechPlayHandle">{{ speech.text }}</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <div class="table" :style="{ height: table.height }">
            <el-table :data="table.tableList" ref="table" v-loading="table.loading" @row-click="tableHandleRowClick" highlight-current-row bordersize="mini" height="100%" style="width: 100%" center border>
                <el-table-column prop="name" min-width="200" label="Name"> </el-table-column>
                <el-table-column prop="common" min-width="150" label="common"></el-table-column>
                <el-table-column prop="pronounce" min-width="100" label="Pronounce"> </el-table-column>
                <el-table-column prop="verb" min-width="150" label="Verb" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="noun" min-width="150" label="Noun" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="adjective" min-width="150" label="Adjective" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="adverb" min-width="150" label="Adverb" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="conjunction" min-width="150" label="Conjunction" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="preposition" min-width="150" label="Preposition" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column label="Operation" min-width="300">
                    <template slot-scope="scope">
                        <div class="panel">
                            <el-button type="primary" size="mini" @click="editDialogEdit(scope.row)">Edit</el-button>
                            <el-button type="danger" size="mini" @click="editDelete(scope.row)">Delete</el-button>
                            <el-button type="primary" size="mini" @click="exampleEditDialog(scope.row)">Example</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination background @size-change="pagerHandleSizeChange" @current-change="pagerHandleCurrentChange" :current-page="table.searchForm.pageNo" :page-sizes="[10, 20, 50, 100]" :page-size="table.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="table.total"> </el-pagination>
        </div>
        <!-- 新建或编辑单词弹窗 -->
        <el-dialog :title="editDialog.title" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="editDialog.visible" @close="editDialogClose" @opened="editDialogOpen" width="1200px" top="0" center>
            <span>
                <el-form :inline="true" :rules="editDialog.rules" :model="editDialog.form" ref="editDialogForm" class="demo-form-inline jiaoyan" label-width="120px" size="mini">
                    <el-form-item label="词名:" prop="name">
                        <el-input placeholder="" v-model="editDialog.form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="发音:" prop="pronounce">
                        <el-input placeholder="" v-model="editDialog.form.pronounce"></el-input>
                    </el-form-item>
                    <el-form-item label="常见含义:" prop="common">
                        <el-input placeholder="" v-model="editDialog.form.common"></el-input>
                    </el-form-item>
                    <br />
                    <div>
                        <el-form-item label="复数:" prop="nounPlural">
                            <el-input placeholder="" v-model="editDialog.form.nounPlural"></el-input>
                        </el-form-item>
                        <el-form-item label="第三人称:" prop="verbThirdPersonSingular" class="vb">
                            <el-input placeholder="" v-model="editDialog.form.verbThirdPersonSingular"></el-input>
                        </el-form-item>
                    </div>
                    <div>
                        <el-form-item label="过去式:" prop="verbPastTense" class="vb">
                            <el-input placeholder="" v-model="editDialog.form.verbPastTense"></el-input>
                        </el-form-item>
                        <el-form-item label="过去分词:" prop="verbPastParticiple" class="vb">
                            <el-input placeholder="" v-model="editDialog.form.verbPastParticiple"></el-input>
                        </el-form-item>
                        <el-form-item label="现在分词:" prop="verbPresentParticiple" class="vb">
                            <el-input placeholder="" v-model="editDialog.form.verbPresentParticiple"></el-input>
                        </el-form-item>
                    </div>
                    <el-descriptions direction="vertical" :column="2" :labelStyle="{ background: '#409EFF', color: '#fff', 'text-align': 'center' }" border>
                        <el-descriptions-item label="名词">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.noun" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="动词">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.verb" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="形容词">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.adjective" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="副词">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.adverb" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="介词">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.preposition" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="连词">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.conjunction" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="Comment">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.comment" maxlength="1024"></el-input>
                        </el-descriptions-item>
                        <el-descriptions-item label="Paste">
                            <el-input placeholder="" type="textarea" v-model="copiedText" maxlength="1024" ref="parseBox"></el-input>
                        </el-descriptions-item>
                    </el-descriptions>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editParseData">Parse</el-button>
                <el-button @click="editDialog.visible = false">Cancle</el-button>
                <el-button type="primary" @click="editDialogSave">Confirm</el-button>
            </span>
        </el-dialog>
        <!-- 新建或编辑示例弹窗 -->
        <el-dialog :title="dataExampleDialog.title" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="dataExampleDialog.visible" @close="exampleEditDialogClose" @opened="exampleEditDialogOpen" width="1200px" top="0" center>
            <el-descriptions :title="dataExampleDialog.form.name + ' | ' + dataExampleDialog.form.common" direction="vertical" :column="2" :labelStyle="{ background: '#409EFF', color: '#fff', 'text-align': 'center' }" border>
                <el-descriptions-item label="名词">
                    {{ dataExampleDialog.form.noun ? dataExampleDialog.form.noun : "无" }}
                </el-descriptions-item>
                <el-descriptions-item label="动词">
                    {{ dataExampleDialog.form.verb ? dataExampleDialog.form.verb : "无" }}
                </el-descriptions-item>
                <el-descriptions-item label="形容词">
                    {{ dataExampleDialog.form.adjective ? dataExampleDialog.form.adjective : "无" }}
                </el-descriptions-item>
                <el-descriptions-item label="副词">
                    {{ dataExampleDialog.form.adverb ? dataExampleDialog.form.adverb : "无" }}
                </el-descriptions-item>
                <el-descriptions-item label="介词">
                    {{ dataExampleDialog.form.preposition ? dataExampleDialog.form.preposition : "无" }}
                </el-descriptions-item>
                <el-descriptions-item label="连词">
                    {{ dataExampleDialog.form.conjunction ? dataExampleDialog.form.conjunction : "无" }}
                </el-descriptions-item>
                <el-descriptions-item label="示例">
                    <div v-for="(example, index) in dataExampleDialog.exampleArray" :key="index" class="example-item">
                        <div class="example-item-inner">
                            <el-divider content-position="left">{{ example.key }}</el-divider>
                            <el-button type="danger" @click="exampleDelete(index)" icon="el-icon-delete" size="mini"></el-button>
                            <el-input placeholder="" type="textarea" :value="example.value" maxlength="1024"></el-input>
                        </div>
                    </div>
                </el-descriptions-item>
                <el-descriptions-item label="添加示例">
                    <div class="example-panel">
                        <el-input placeholder="" v-model="dataExampleDialog.exampleKey"></el-input>
                        <el-input placeholder="" v-model="dataExampleDialog.exampleContent" type="textarea" maxlength="1024" style="margin: 10px 0; border-radius: 4px; border: 1px solid rgb(88, 204, 2)"></el-input>
                        <el-button type="primary" @click="appendExamples">+</el-button>
                    </div>
                </el-descriptions-item>
            </el-descriptions>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dataExampleDialog.visible = false">Cancle</el-button>
                <el-button type="primary" @click="exampleEditDialogSave">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    /* eslint-disable */
    import { itemList, itemAdd, itemEdit, itemDelete, itemGenerate, itemExampleEdit, ttsGenerate } from "@/api/request";
    import { Message } from "element-ui";
    export default {
        data() {
            return {
                speech: {
                    currentIndex: 0,
                    isPlaying: false,
                    text: "PlayList",
                },
                copiedText: "",
                table: {
                    height: "500px",
                    loading: false,
                    total: 0,
                    tableList: [],
                    searchForm: {
                        pageNo: 1,
                        pageSize: 10,
                    },
                },
                editDialog: {
                    title: "Create Item",
                    operation: "add",
                    form: {},
                    visible: false,
                    rules: {
                        name: [{ required: true, message: "Please input item name", trigger: "blur" }],
                    },
                },
                dataExampleDialog: {
                    title: "Create Example",
                    operation: "add",
                    form: {},
                    visible: false,
                    rules: {
                        name: [{ required: true, message: "Please input item name", trigger: "blur" }],
                    },
                    exampleArray: [],
                    exampleKey: "",
                    exampleContent: "",
                },
            };
        },
        created() {
            this.tableGetList(this.table.searchForm);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.table.height = pH - h - 120 + "px";
        },
        methods: {
            // 编辑窗口: 关闭对话框后(渲染结束)执行. 恢复窗口初始状态
            editDialogClose() {
                this.$nextTick(() => {
                    this.editDialog.form = {};
                    this.editDialog.title = "Create Item";
                    this.editDialog.operation = "add";
                    this.copiedText = "";
                });
            },
            exampleEditDialogClose() {
                this.$nextTick(() => {
                    this.dataExampleDialog.form = {};
                    this.dataExampleDialog.title = "Create Example";
                    this.dataExampleDialog.exampleArray = [];
                    this.dataExampleDialog.exampleKey = "";
                    this.dataExampleDialog.exampleContent = "";
                    this.tableGetList(this.table.searchForm);
                });
            },
            // 编辑窗口: 打开. 通用设置
            editDialogOpen() {
                this.editDialog.visible = true;
            },
            exampleEditDialogOpen() {
                this.dataExampleDialog.visible = true;
            },
            ttsGenerate() {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                ttsGenerate().then(
                    (res) => {
                        loading.close();
                        this.$message({
                            message: "success",
                            type: "success",
                        });
                    },
                    (res) => {
                        loading.close();
                        this.$message({
                            message: "success",
                            type: "success",
                        });
                    },
                );
            },
            itemGenerate() {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                itemGenerate().then(
                    (res) => {
                        loading.close();
                        this.$message({
                            message: "success",
                            type: "success",
                        });
                    },
                    (res) => {
                        loading.close();
                        this.$message({
                            message: "success",
                            type: "success",
                        });
                    },
                );
            },
            // 编辑窗口: 编辑. 同时触发editDialogOpen()
            editDialogEdit(row) {
                console.log(row, "row:");
                this.editDialog.title = "Edit Item";
                this.editDialog.visible = true;
                this.editDialog.operation = "edit";
                this.editDialog.form = row;
            },
            exampleEditDialog(row) {
                this.dataExampleDialog.title = "Edit Examples";
                this.dataExampleDialog.visible = true;
                this.dataExampleDialog.exampleArray = row.example.examples ? JSON.parse(row.example.examples) : [];
                this.dataExampleDialog.form = row;
            },
            editDialogDelete(row) {},
            editDialogSave() {
                this.$refs.editDialogForm.validate((valid) => {
                    if (valid) {
                        // 全屏遮罩. 不是表格遮罩
                        const loading = this.$loading({
                            lock: true,
                            text: "Loading...",
                        });
                        if (this.editDialog.operation == "add") {
                            itemAdd(this.editDialog.form)
                                .then(
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "success",
                                            duration: 800,
                                            onClose: () => {
                                                console.log("res", res);
                                                loading.close();
                                                this.editDialog.visible = false;
                                                this.tableGetList(this.table.searchForm);
                                            },
                                        });
                                    },
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "error",
                                            duration: 1500,
                                            onClose: () => {
                                                loading.close();
                                            },
                                        });
                                    },
                                )
                                .catch((error) => {
                                    console.log(error);
                                });
                        } else if (this.editDialog.operation == "edit") {
                            itemEdit(this.editDialog.form)
                                .then(
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "success",
                                            duration: 800,
                                            onClose: () => {
                                                console.log("res", res);
                                                loading.close();
                                                this.editDialog.visible = false;
                                                this.tableGetList(this.table.searchForm);
                                            },
                                        });
                                    },
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "error",
                                            duration: 1500,
                                            onClose: () => {
                                                loading.close();
                                            },
                                        });
                                    },
                                )
                                .catch((error) => {
                                    this.$message({
                                        showClose: true,
                                        message: error instanceof Object && error.message !== undefined ? error.message : "Cannot parse response",
                                        type: "error",
                                        onClose() {},
                                    });
                                });
                        }
                    }
                });
            },
            exampleEditDialogSave() {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                itemExampleEdit({
                    id: this.dataExampleDialog.form.example.id,
                    name: this.dataExampleDialog.form.example.name,
                    examples: this.dataExampleDialog.exampleArray.length > 0 ? JSON.stringify(this.dataExampleDialog.exampleArray) : null,
                })
                    .then(
                        (res) => {
                            Message({
                                message: `${res.message}`,
                                type: "success",
                                duration: 800,
                                onClose: () => {
                                    loading.close();
                                    this.dataExampleDialog.visible = false;
                                    this.tableGetList(this.table.searchForm);
                                },
                            });
                        },
                        (res) => {
                            Message({
                                message: `${res.message}`,
                                type: "error",
                                duration: 1500,
                                onClose: () => {
                                    loading.close();
                                },
                            });
                        },
                    )
                    .catch((error) => {
                        this.$message({
                            showClose: true,
                            message: error instanceof Object && error.message !== undefined ? error.message : "Cannot parse response",
                            type: "error",
                            onClose() {},
                        });
                    });
            },
            editParseData() {
                if (this.copiedText) {
                    var regs = [
                        {
                            property: "name",
                            reg: /^([a-zA-Z ]+)/,
                        },
                        {
                            property: "pronounce",
                            reg: /\/(.*?)\//g,
                        },
                        {
                            property: "noun",
                            reg: /(n)\.\s*([^\n]+)/,
                        },
                        {
                            property: "verb",
                            reg: /(v|vi|vt)\.\s*([^\n]+)/,
                        },
                        {
                            property: "adjective",
                            reg: /(adj)\.\s*([^\n]+)/,
                        },
                        {
                            property: "adverb",
                            reg: /(adv)\.\s*([^\n]+)/,
                        },
                        {
                            property: "conjunction",
                            reg: /(conj)\.\s*([^\n]+)/,
                        },
                        {
                            property: "pronoun",
                            reg: /(pron)\.\s*([^\n]+)/,
                        },
                        {
                            property: "preposition",
                            reg: /(prep)\.\s*([^\n]+)/,
                        },
                        {
                            property: "nounPlural",
                            reg: /复数(.*?)(\w+)/,
                        },
                        {
                            property: "verbPastTense",
                            reg: /过去式(.*?)(\w+)/,
                        },
                        {
                            property: "verbPastParticiple",
                            reg: /过去分词(.*?)(\w+)/,
                        },
                        {
                            property: "verbPresentParticiple",
                            reg: /现在分词(.*?)(\w+)/,
                        },
                        {
                            property: "verbThirdPersonSingular",
                            reg: /第三人称单数(.*?)(\w+)/,
                        },
                    ];
                    var baseArr = ["name", "pronounce"];
                    var classArr = ["noun", "verb", "adjective", "adverb", "conjunction", "pronoun", "preposition"];
                    var tagArr = ["nounPlural", "verbPastTense", "verbPastParticiple", "verbPresentParticiple", "verbThirdPersonSingular"];
                    for (var i = 0; i < regs.length; i++) {
                        var matches = this.copiedText.match(regs[i]["reg"]);
                        if (matches) {
                            console.log(regs[i]["property"], matches);
                            var result1 = matches[1];
                            var result2 = matches[2];
                            if (baseArr.includes(regs[i]["property"])) {
                                if (result1) {
                                    this.$set(this.editDialog.form, regs[i]["property"], result1);
                                }
                            }
                            if (classArr.includes(regs[i]["property"])) {
                                if (result1 && result2) {
                                    this.$set(this.editDialog.form, regs[i]["property"], result1 + "." + result2);
                                }
                            }
                            if (tagArr.includes(regs[i]["property"])) {
                                if (result2) {
                                    this.$set(this.editDialog.form, regs[i]["property"], result2);
                                }
                            }
                        }
                    }
                }
            },
            editStatus(row, value) {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                itemEdit({ ...row, status: value }).then(
                    (res) => {
                        console.log("res", res);
                        loading.close();
                        this.tableGetList(this.table.searchForm);
                    },
                    (res) => {
                        Message({
                            message: res.message,
                            type: "error",
                            duration: 1500,
                            onClose: () => {
                                loading.close();
                            },
                        });
                    },
                );
            },
            editDelete(row) {
                this.$confirm("Are you sure you want to delete this item?", "Tips", {
                    confirmButtonText: "Confirm",
                    cancelButtonText: "Cancel",
                }).then(
                    () => {
                        const loading = this.$loading({
                            lock: true,
                            text: "Loading...",
                        });
                        itemDelete(row).then(
                            (res) => {
                                loading.close();
                                this.$message({
                                    message: res.message,
                                    type: "success",
                                });
                                this.tableGetList(this.table.searchForm);
                            },
                            (res) => {
                                this.$message({
                                    showClose: true,
                                    message: res.message,
                                    type: "error",
                                    onClose() {
                                        loading.close();
                                    },
                                });
                            },
                        );
                    },
                    () => {},
                );
            },
            // 表格: 列表查询方法
            tableGetList(searchForm) {
                itemList(searchForm.createTime ? { ...searchForm, createTime: `${searchForm.createTime[0]},${searchForm.createTime[1]}` } : { ...searchForm })
                    .then(
                        (res) => {
                            this.table.total = res.data.total;
                            this.table.tableList = res.data.list;
                            console.log("this.table.tableList", this.table.tableList);
                        },
                        (res) => {
                            Message({
                                message: `${res.message}`,
                                type: "error",
                                duration: 1500,
                            });
                        },
                    )
                    .catch((error) => {
                        Message({
                            message: `${error}`,
                            type: "error",
                            duration: 1500,
                        });
                    });
            },
            // 表格: 点击重置搜索条件时
            tableResetForm() {
                this.table.searchForm = {
                    pageNo: 1,
                    pageSize: 10,
                };
                this.tableGetList(this.table.searchForm);
            },
            // 表格: 点击表格的行时
            tableHandleRowClick(row, column, event) {
                this.$refs.table.toggleRowSelection(row);
                if (row.tts) {
                    this.speechPlayOne(row);
                }
            },
            // 表格: 点击搜索按钮时
            tableSearch() {
                this.speechStopHandle();
                this.table.searchForm = { ...this.table.searchForm, pageNo: 1, pageSize: 10 };
                console.log(this.table.searchForm);
                this.tableGetList(this.table.searchForm);
            },
            // 分页器: 每页条数发生变化时
            pagerHandleSizeChange(val) {
                this.table.searchForm.pageSize = val;
                this.tableGetList(this.table.searchForm);
            },
            // 分页器: 翻页时
            pagerHandleCurrentChange(val) {
                this.table.searchForm.pageNo = val;
                this.tableGetList(this.table.searchForm);
            },
            speechPlayOne(row) {
                if (row.tts) {
                    const audio = this.$refs.audio;
                    audio.src = "data:audio/wav;base64," + row.tts.speech;
                    audio.load();
                    audio.play();
                }
            },
            speechPlayList() {
                if (this.table.tableList.length > 0) {
                    const audio = this.$refs.audioList;
                    audio.src = "data:audio/wav;base64," + this.table.tableList[this.speech.currentIndex].tts.speech;
                    audio.load();
                    audio.play();
                    this.speech.currentIndex++;
                    if (this.speech.currentIndex >= this.table.tableList.length) {
                        this.speech.currentIndex = 0;
                    }
                }
            },
            speechPlayHandle() {
                if (!this.speech.isPlaying) {
                    this.speech.isPlaying = true;
                    this.speech.currentIndex = 0;
                    this.speech.text = "Playing...";
                    this.$refs.audioList.addEventListener("ended", this.speechPlayList, false);
                    this.speechPlayList();
                } else {
                    this.speechStopHandle();
                }
            },
            speechStopHandle() {
                this.speech.isPlaying = false;
                this.speech.text = "Playing";
                this.$refs.audioList.removeEventListener("ended", this.speechPlayList, false);
            },
            handleEnter() {
                this.tableSearch();
            },
            appendExamples() {
                let obj = {};
                obj["key"] = this.dataExampleDialog.exampleKey;
                obj["value"] = this.dataExampleDialog.exampleContent;
                this.dataExampleDialog.exampleArray.unshift(obj);
                console.log(this.dataExampleDialog.form);
            },
            exampleDelete(index) {
                console.log(index);
                this.dataExampleDialog.exampleArray = this.dataExampleDialog.exampleArray.filter((v, k, a) => {
                    if (k == index) {
                        return false;
                    } else {
                        return true;
                    }
                });
                console.log(this.dataExampleDialog.exampleArray);
            },
        },
    };
</script>
<style lang="scss" scoped>
    .title {
        display: flex;
        justify-content: space-between;
        padding-right: 10px 10px;
        margin-left: 10px;
        border-bottom: 1px solid #dcdfe6;
        margin-bottom: 10px;
        h4 {
            margin: 0;
            font-size: 14px;
            color: #333;
            padding-bottom: 10px;
            border-bottom: 2px solid rgb(88, 204, 2);
        }
    }
    .searchBox {
        display: flex;
        .searchBtnBox {
            padding-left: 10px;
            // margin-left: 10px;
            ::v-deep .el-form-item--mini.el-form-item,
            ::v-deep .el-form-item--small.el-form-item {
                margin-bottom: 6px !important;
            }
        }
        .inpBox {
            ::v-deep .el-form-item--mini.el-form-item,
            ::v-deep .el-form-item--small.el-form-item {
                margin-bottom: 6px !important;
            }
            ::v-deep .el-form-item.long {
                width: 100%;
                margin-right: 0 !important;
            }
            ::v-deep .el-input__inner {
                width: 150px !important;
            }
            ::v-deep .el-date-editor {
                width: 350px !important;
            }
        }
    }
    .table {
        ::v-deep .el-table {
            font-size: 12px;
            color: #333;
            th {
                background-color: #f5f5f5;
                height: 40px;
                color: #333333 !important;
                padding: 2px 0 !important;
                &:first-child .cell {
                    padding: 0 10px 0 10px;
                }
            }
            tr {
                height: 32px;
                &:nth-child(even) {
                    background-color: #f8fafd;
                }
            }
            td {
                padding: 2px 0 !important;
            }
            .cell {
                text-align: center;
                padding: 0 10px;
            }
            .el-button--primary {
                background-color: rgb(88, 204, 2);
            }
            .el-button--primary:focus,
            .el-button--primary:hover {
                background: #0b59bd;
                border-color: #0b59bd;
                color: #fff;
            }
            .el-button {
                padding: 7px 15px;
            }
            .el-button--text {
                padding: 0;
            }
            .bigBtn {
                padding: 12px 28px;
            }
            .el-button--text {
                color: rgb(204, 0, 0);
            }
            .el-button--text:focus,
            .el-button--text:hover {
                color: #0b59bd;
            }
            .el-table__body tr.hover-row > td {
                background-color: rgba(14, 110, 236, 0.3) !important;
            }
            .el-table__body tr.current-row > td {
                background-color: rgba(14, 110, 236, 0.3) !important;
            }
            .el-table--enable-row-hover .el-table__body tr:hover > td {
                background-color: rgba(14, 110, 236, 0.3) !important;
            }
            .panel {
                margin: 2px 0 !important;
                .el-button--primary {
                    background: #409eff;
                }
                .el-button--danger {
                    color: #fff;
                    background: rgb(204, 0, 0);
                }
            }
        }
    }
    .el-dialog__wrapper {
        ::v-deep .el-dialog__body {
            padding: 15px 15px 15px;
        }
        ::v-deep .el-form-item {
            margin-bottom: 10px;
        }
        ::v-deep .el-form-item__label {
            font-size: 12px;
        }
        ::v-deep .el-divider--horizontal {
            margin: 20px 0;
        }
        ::v-deep .el-dialog__header {
            padding: 8px 20px !important;
            background-color: rgb(88, 204, 2);
            .el-dialog__title {
                line-height: 20px;
                font-size: 14px;
                color: #fff;
            }
            .el-dialog__headerbtn {
                top: 12px;
                .el-dialog__close {
                    color: #fff;
                }
            }
        }
        .hidden ::v-deep .el-upload--picture-card {
            display: none;
        }
        .addressBox .title {
            margin-top: 0;
            margin-left: 10px;
            font-size: 14px;
            margin-bottom: 20px;
            .iconFont {
                color: rgb(88, 204, 2);
                margin-right: 4px;
                font-size: 14px;
            }
        }
        ::v-deep .el-input__inner,
        ::v-deep .el-textarea__inner {
            padding: 4px 8px;
        }
        ::v-deep .el-textarea__inner {
            width: 100% !important;
            height: 120px;
            margin-bottom: 0 !important;
            border: 0;
            resize: none;
        }
        .el-divider__text {
            color: #fff;
            background: rgb(88, 204, 2);
        }
        .el-divider {
            background: rgb(88, 204, 2);
        }
        ::v-deep .el-collapse-item__wrap {
            padding: 30px 0;
            background: #eee;
        }
        ::v-deep .el-collapse-item__header {
            background-color: rgb(88, 204, 2);
            padding-left: 10px;
            height: 32px;
            line-height: 32px;
        }
        .el-form-item.parse {
            width: 100% !important;
            margin: 0;
            ::v-deep .el-form-item__content {
                width: 100% !important;
            }
            ::v-deep .el-textarea__inner {
                width: 98% !important;
                margin: 0 auto;
            }
        }
        ::v-deep .el-descriptions .is-bordered .el-descriptions-item__cell {
            width: 50%;
            padding: 10px !important;

            .example-item {
                background: #ccc;
                padding: 10px;
                margin-bottom: 10px;
                border-radius: 4px;
                &:last-child {
                    margin-bottom: 0;
                }
                .example-item-inner {
                    position: relative;
                    .el-button {
                        position: absolute;
                        top: -16px;
                        right: 0;
                        color: #fff;
                    }
                    .el-input {
                        border: 1px solid rgb(88, 204, 2);
                    }
                }
            }

            .example-panel {
                .el-textarea {
                    .el-textarea__inner {
                        height: 86px;
                    }
                }
            }
        }
    }
</style>
