import {QueryClient, QueryClientProvider} from "react-query";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import MainPage from "../MainPage/MainPage";
import React from "react";
import Nav from "../Nav/Nav";
import BaseComponent from "../Base/BaseComponent";
import SignIn from "../SignIn/SignIn";
import SignUp from "../SignUp/SignUp";
import AllPlacesLists from "../PlacesLists/AllPlacesLists";
import MyPlacesLists from "../PlacesLists/MyPlacesLists";
import AddPlacesList from "../PlacesLists/AddPlacesList";
import PlacesList from "../PlacesLists/PlacesList";


export default class App extends BaseComponent {
    render() {
        return (
            <QueryClientProvider client={new QueryClient()}>
                <BrowserRouter>
                    <div>
                        <Nav placesService={this.placesService} />
                        <Switch>
                            <Route path="/my-places-lists">
                                <MyPlacesLists placesService={this.placesService}/>
                            </Route>
                            <Route path="/places-lists">
                                <AllPlacesLists placesService={this.placesService}/>
                            </Route>
                            <Route path="/places-list/:id">
                                <PlacesList placesService={this.placesService}/>
                            </Route>
                            <Route path="/add-places-list">
                                <AddPlacesList placesService={this.placesService}/>
                            </Route>
                            <Route path="/sign-in">
                                <SignIn placesService={this.placesService}/>
                            </Route>
                            <Route path="/sign-up">
                                <SignUp placesService={this.placesService}/>
                            </Route>
                            <Route path="/">
                                <MainPage placesService={this.placesService}/>
                            </Route>
                        </Switch>
                    </div>
                </BrowserRouter>
            </QueryClientProvider>
        )
    }
}