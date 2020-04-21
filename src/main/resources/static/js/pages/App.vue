<template>
    <v-app dark>
        <v-toolbar >
            <v-toolbar-title>Image board</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}&nbsp;</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>{{ logout }}</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <v-container v-if="!profile">Необходимо авторизоваться через
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <messages-list :messages="messages" />
            </v-container>
        </v-content>
    </v-app>
</template>
<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import { addHandler } from 'util/ws'
    import { mdiExitToApp } from '@mdi/js'
    import { mdiDeleteForever } from '@mdi/js'
    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile,
                logout: mdiExitToApp,
                delete: mdiDeleteForever
            }
        },
        created() {
            addHandler(data => {
                if(data.objectType === 'MESSAGE') {
                    const index = this.message.findIndex(item => item.id === data.body.id)
                    switch (data.eventType) {
                        case 'CREATE':
                        case 'UPDATE':
                            if(index>-1){
                                this.messages.splice(index, 1, data.body)
                            }else{
                                this.messages.push(data.body)
                            }
                            break
                        case 'REMOVE':
                            this.messages.splice(index, 1)
                            break
                        default:
                            console.error('event type is unknown ${data.eventType}')
                    }
                }else{
                    console.error('object type is unknown ${data.objectType}')
                }
            })
        }
    }
</script>

<style>

</style>