<template>
    <svg :class="classList" aria-hidden="true" v-on="$listeners">
        <use :xlink:href="symbolID" />
    </svg>
</template>
<script>
    export default {
        name: "SvgIcon",
        props: {
            symbolIdSuffix: {
                type: String,
                required: true,
            },
            otherClass: {
                type: String,
                default: "",
            },
        },
        computed: {
            symbolID() {
                return `#icon-${this.symbolIdSuffix}`;
            },
            classList() {
                if (this.otherClass) {
                    return "svg-icon " + this.otherClass;
                } else {
                    return "svg-icon";
                }
            },
        },
    };
</script>
<style lang="scss" scoped>
    .svg-icon {
        width: 1.2rem;
        height: 1.2rem;
        vertical-align: -0.26rem;
        fill: currentColor;
        overflow: hidden;
    }
</style>
