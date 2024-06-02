module.exports = {
    root: true, // 根配置, 无需向上寻找父级eslint规则.
    env: {
        node: true,
    },
    // rules集合.
    // plugin:vue/essential: Vue.js推荐的规则, 属于npm包@vue/cli-plugin-eslint的一部分.
    // eslint:recommended: eslint官方推荐的规则, 内置于eslint
    extends: ["plugin:vue/essential", "eslint:recommended"],
    // 分析代码是否合规的"解析器"
    parserOptions: {
        parser: "babel-eslint",
    },
    // 重写extends规则集合.
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off", // 生产环境禁止代码中含有console.log
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off", // 生产环境禁止代码中含有debugger
        eqeqeq: "warn", // 必须使用===, 否则无法build成功
        curly: "error", // 单if语句后面的必须使用{}括起来, 可以自动修复
        quotes: ["warn", "double"], // 要使用"", 可以自动修复
        semi: ["warn", "always"], // 语句末尾总需要分号
        // indent: ["warn", 4, { baseIndent: 1 }], // 一个缩进等于多少空格. VSCode Editor: Tab Size规定一个tab等于几个空格
        "comma-dangle": ["warn", "always-multiline"], // 最后一个元素+换行才要求加逗号
        "spaced-comment": ["warn", "always"], // 注释分界符后面必须加空格比如// comment合法, //comment报错
        "space-before-function-paren": [
            // 定义函数时，圆括号两侧必须有空格. 自动修复
            "error",
            { anonymous: "always", named: "never", asyncArrow: "always" }, // named函数不要求
        ],
        "vue/script-indent": ["error", 4, { baseIndent: 1 }], // script内默认无缩进. baseIndent规定默认有一个基础缩进, 且一个缩进等于4个空格
    },
};
