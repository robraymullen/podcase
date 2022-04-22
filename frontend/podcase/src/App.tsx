import React, { useState, useEffect, useReducer } from 'react';
import { styled, alpha } from '@mui/material/styles';
import './App.css';
import PodcastGrid from './components/PodcastGrid/PodcastGrid';
import PodcastList from './components/PodcastList/PodcastList';
import SideBar from "./components/SideBar/SideBar";
import Box from '@mui/material/Box';
import { Toolbar } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { GridRoutes, SubscribedEpisode, Podcast, User, initialAppState, SubscribedPodcast } from './Types';
import Playbar from './components/Playbar/Playbar';
import { getAllUsers, getMostRecentPlayedEpisode, getUserSubscriptions } from './services/PodcaseAPIService';
import Header from './components/Header/Header';
import Users from './components/Users/Users';
import { AppContext } from './context/context';
import { changeEpisode, changeSubscriptions, changeUser, stateReducer, setAutoPlay, changePlayMessage } from './context/reducer';
import Admin from './components/Admin/Admin';
import SearchResult from './components/SearchResult/SearchResult';
import Alert from '@mui/material/Alert';
import Button from '@mui/material/Button';
import AddPodcast from './components/AddPodcast/AddPocast';

function App() {

  const [currentEpisode, setCurrentEpisode] = useState<SubscribedEpisode>(); //TODO use SubscribedEpisode when backend is updated
  const [podcasts, setPodcasts] = useState<Podcast[]>([]);
  const [headerText, setHeaderText] = useState<string>("");
  const [state, dispatch] = useReducer(stateReducer, initialAppState);

  useEffect(() => {
    if (initialAppState.currentUser == null) {
      getAllUsers((users: User[]) => {
        dispatch(changeUser(users[0]));
        getMostRecentPlayedEpisode(users[0].id, (episode: SubscribedEpisode) => {
          dispatch(setAutoPlay(false));
          dispatch(changeEpisode(episode));
        }, () => {console.log("error getting recent episode") });
        getUserSubscriptions(users[0].id, (podcasts: SubscribedPodcast[]) => {dispatch(changeSubscriptions(podcasts))}, () => {});
      }, () => { console.log("error getting user")});
    }
  }, []);

  return (
    <AppContext.Provider value={{ state, dispatch }}>
      <BrowserRouter>
        <Box sx={{ display: 'flex' }}>
          <CssBaseline />
          <Header></Header>
          <SideBar></SideBar>
          <Box component="main" sx={{ width: "100%" }}>
            <Box
              sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3, paddingBottom: "150px", minWidth: "100%" }}
            >
              <Toolbar />
              <Routes>
                <Route path="/" element={<PodcastGrid state={GridRoutes.PODCAST_SUBSCRIPTION} podcasts={state.userSubscriptions} setPodcasts={changeSubscriptions}></PodcastGrid>} />
                <Route path="all" element={<PodcastGrid state={GridRoutes.PODCAST_ALL} podcasts={podcasts} setPodcasts={setPodcasts}></PodcastGrid>} />
                <Route path="/podcast/:id" element={<PodcastList setHeaderText={setHeaderText}></PodcastList>} />
                <Route path="/search" element={<SearchResult></SearchResult> } />
                <Route path="/add/subscription" element={<AddPodcast></AddPodcast>} />
                <Route path="/users/change" element={<Users></Users>} />
                <Route path="/admin" element={<Admin></Admin>}/>
              </Routes>
            </Box>
            <Box
            sx={{
              position: 'fixed',
                bottom: "150px",
                width: "calc(100% - 240px)",
                height: "50px",
            }}>
              {
                state.playMessage.visible && <Alert severity={state.playMessage.severity} 
                onClose={() => {dispatch(changePlayMessage({severity: "info", visible: false, text: ""}))}}
                >
                  {state.playMessage.text}</Alert>
              }
            </Box>
            <Playbar></Playbar>
          </Box>

        </Box>
      </BrowserRouter>
    </AppContext.Provider>



  );
}

export default App;
