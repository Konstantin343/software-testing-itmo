import React from "react";
import BaseComponent from "../Base/BaseComponent";
import Preview from "../Preview/Preview";

export default class Unauthorized extends BaseComponent {

    constructor(props) {
        super(props);

        this.openSignInPage = this.openSignInPage.bind(this)
        this.openSignUpPage = this.openSignUpPage.bind(this)
    }

    openSignInPage() {
        window.location.href = '/sign-in'
    }

    openSignUpPage() {
        window.location.href = '/sign-up'
    }

    render() {
        return (
            <div>
                <Preview placesService={this.placesService}/>
                <center>
                    <div className={"login-button-holder"}>
                        <button className={"login-button"} onClick={this.openSignInPage}>Sign in</button>
                    </div>
                    <div className={"register-button-holder"}>
                        <button className={"register-button"} onClick={this.openSignUpPage}>Sign up</button>
                    </div>
                </center>
            </div>
        )
    }
}