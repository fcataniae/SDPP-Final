import React from "react";
import {Route, Switch} from "react-router";
import NotFound from "../error/NotFound";
import Home from "../home/Home";
import Results from "../results/Results";

export default class Routes extends React.Component {

    render(){
        return (
            <Switch>
                <Route exact path="/" component={Home} />
                <Route path="/results" component={Results} />
                <Route path="*" component={NotFound} />
            </Switch>
        );
    }
}