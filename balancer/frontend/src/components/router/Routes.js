import React from "react";
import {Route, Switch} from "react-router";
import NotFound from "../error/NotFound";
import Home from "../home/Home";

export default class Routes extends React.Component {

    render(){
        return (
            <Switch>
                <Route exact path="/" component={Home} />
                <Route path="/messages2" component={Home} />
                <Route path="*" component={NotFound} />
            </Switch>
        );
    }
}