const path = require("path");
const webpack = require("webpack");
const settings = require("./settings");

module.exports = {
    publicPath: "/", // BASE_URL
    devServer: {
        port: 80,
        open: true,
        overlay: {
            warnings: false,
            errors: true,
        },
        proxy: settings.APP_HOST,
    },
    configureWebpack: {
        externals: {
            AMap: "AMap", // 高德地图配置
        },
        plugins: [
            new webpack.DefinePlugin({
                "process.env": {
                    APP: JSON.stringify(settings),
                },
            }),
        ],
    },
    chainWebpack(config) {
        config.module.rule("svg").exclude.add(path.resolve(__dirname, "src/icons")).end();
        config.module
            .rule("icons")
            .test(/\.svg$/)
            .include.add(path.resolve(__dirname, "src/icons"))
            .end()
            .use("svg-sprite-loader")
            .loader("svg-sprite-loader")
            .options({
                symbolId: "icon-[name]", // svg标签内的symbol标签id
            })
            .end();
        config.plugin("html").tap((args) => {
            const isProduction = process.env.NODE_ENV === "production";
            args[0].template = isProduction ? "public/index.html" : "public/index.dev.html";
            return args;
        });
    },
};
