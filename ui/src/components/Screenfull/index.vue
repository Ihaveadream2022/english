<template>
    <div>
        <!-- <svg-icon :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'" @click="click" /> -->
        <svg-icon symbol-id-suffix="fullscreen" @click="toggle" />
    </div>
</template>

<script>
    import screenfull from "screenfull";
    export default {
        name: "componentScreenFull",
        data() {
            return {
                isFullscreen: false,
            };
        },
        mounted() {
            this.init();
        },
        beforeDestroy() {
            this.destroy();
        },
        methods: {
            toggle() {
                console.log(screenfull);
                if (!screenfull.enabled) {
                    this.$message({
                        message: "you browser can not work",
                        type: "warning",
                    });
                    return false;
                }
                screenfull.toggle();
            },
            change() {
                this.isFullscreen = screenfull.isFullscreen;
            },
            init() {
                if (screenfull.enabled) {
                    screenfull.on("change", this.change);
                }
            },
            destroy() {
                if (screenfull.enabled) {
                    screenfull.off("change", this.change);
                }
            },
        },
    };
</script>

<style scoped>
    .svg-icon {
        cursor: pointer;
        width: 1.2rem;
        height: 1.2rem;
    }
</style>
