<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title class="px-8">Image board</v-toolbar-title>
            <v-btn text

                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showMessages">
                Messages
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/profile'"
                   @click="showProfile">
                {{profile.name}}
            </v-btn>
            <v-btn class="px-8" v-if="profile" icon href="/logout">
                <v-icon>{{ logout }}</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>
<script>
    import MessagesList from 'pages/MessageList.vue'
    import { addHandler } from 'util/ws'
    import { mapState, mapMutations } from 'vuex'
    import { mdiExitToApp } from '@mdi/js'
    import { mdiDeleteForever } from '@mdi/js'
    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                logout: mdiExitToApp,
                delete: mdiDeleteForever
            }
        },
        computed: mapState(['profile']),
        methods: {
            ...mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
            showMessages() {
                this.$router.push('/')
            },
            showProfile() {
                this.$router.push('/profile')
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like the object type if unknown "${data.objectType}"`)
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>

</style>
<!--<v-icon>{{ logout }}</v-icon>-->
