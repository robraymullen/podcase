import { useState, useEffect } from 'react';
import { styled, alpha } from '@mui/material/styles';
import './App.css';
import PodcastGrid from './components/PodcastGrid/PodcastGrid';
import PodcastList from './components/PodcastList/PodcastList';
import SideBar from "./components/SideBar/SideBar";
import Box from '@mui/material/Box';
import { Toolbar } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';
import InputBase from '@mui/material/InputBase';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { GridRoutes, SubscribedEpisode, Podcast } from './Types';
import Playbar from './components/Playbar/Playbar';
import {getMostRecentPlayedEpisode} from './services/PodcaseAPIService';
import Header from './components/Header/Header';

function App() {

  const [currentEpisode, setCurrentEpisode] = useState<SubscribedEpisode>(); //TODO use SubscribedEpisode when backend is updated
  const [podcasts, setPodcasts] = useState<Podcast[]>([]);
  const [headerText, setHeaderText] = useState<string>("");
  
  useEffect(() => {
    if (!currentEpisode) {
      getMostRecentPlayedEpisode(setCurrentEpisode, ()=>{});
    }
  }, []);

  return (
    <BrowserRouter>
      <Box sx={{ display: 'flex' }}>
        <CssBaseline />
        <Header headerText={headerText}></Header>
        <SideBar></SideBar>
        <Box component="main" sx={{ width: "100%" }}>
          <Box
            sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3, paddingBottom: "150px", minWidth: "100%" }}
          >
            <Toolbar />
            <Routes>
              <Route path="/" element={<PodcastGrid state={GridRoutes.PODCAST_SUBSCRIPTION} podcasts={podcasts} setPodcasts={setPodcasts}></PodcastGrid>} />
              <Route path="all" element={<PodcastGrid state={GridRoutes.PODCAST_ALL} podcasts={podcasts} setPodcasts={setPodcasts}></PodcastGrid>} />
              <Route path="/podcast/:id" element={<PodcastList setCurrentEpisode={setCurrentEpisode} setHeaderText={setHeaderText}></PodcastList>} />
            </Routes>
          </Box>
          <Playbar currentEpisode={currentEpisode}></Playbar>
        </Box>

      </Box>
    </BrowserRouter>


  );
}

export default App;
