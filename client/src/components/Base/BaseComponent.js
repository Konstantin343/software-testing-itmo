import React from "react";

export default class BaseComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: null
        }

        this.placesService = props.placesService
        this.reloadUser = this.reloadUser.bind(this)
    }

    reloadUser() {
        this.placesService.currentUser().then(res => {
            this.setState({
                user: res[0]
            })
        })
    }
}