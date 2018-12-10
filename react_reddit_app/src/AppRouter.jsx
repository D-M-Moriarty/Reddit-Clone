import React, { Component } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import App from "./App";
import CreateSubreddit from "./components/CreatePost";
import ViewPost from "./components/ViewPost";
import PostComment from "./components/CreatePost";
import NavBar from "./components/NavBar";
import SubredditPosts from "./components/SubredditPosts";

class AppRouter extends Component {
  state = {};

  render() {
    return (
      <BrowserRouter>
        <div>
          <NavBar />
          <Switch>
            <Route path="/r/" component={App} exact />
            <Route path="/submit" component={CreateSubreddit} />
            <Route path="/r/ViewPost" component={ViewPost} />
            <Route path="/r/PostComment" component={PostComment} />
            {/* <Route path="/r/:id/posts" component={SubredditPosts} /> */}
            <Route component={App} />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default AppRouter;
