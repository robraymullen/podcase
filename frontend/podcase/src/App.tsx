import React, { useState, useEffect } from 'react';
import { styled, alpha } from '@mui/material/styles';
import './App.css';
import PodcastGrid from './components/PodcastGrid/PodcastGrid';
import PodcastList from './components/PodcastList/PodcastList';
import SideBar from "./components/SideBar/SideBar";
import Box from '@mui/material/Box';
import { Divider, Drawer, List, ListItem, ListItemText, Toolbar } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Typography from '@mui/material/Typography';
import AddBoxIcon from '@mui/icons-material/AddBox';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import { GridRoutes, SubscribedEpisode, Episode } from './Types';
import Playbar from './components/Playbar/Playbar';
import {getMostRecentPlayedEpisode} from './services/PodcaseAPIService';

function App() {

  const [currentEpisode, setCurrentEpisode] = useState<Episode>(); //TODO use SubscribedEpisode when backend is updated

  useEffect(() => {
    if (!currentEpisode) {
      getMostRecentPlayedEpisode(setCurrentEpisode, ()=>{});
    }
  }, []);

  const container = window !== undefined ? () => window.document.body : undefined;
  const drawerWidth = 240;

  const Search = styled('div')(({ theme }: any) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(1),
      width: 'auto',
    },
  }));

  const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  }));

  const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
      padding: theme.spacing(1, 1, 1, 0),
      // vertical padding + font size from searchIcon
      paddingLeft: `calc(1em + ${theme.spacing(4)})`,
      transition: theme.transitions.create('width'),
      width: '100%',
      [theme.breakpoints.up('sm')]: {
        width: '12ch',
        '&:focus': {
          width: '20ch',
        },
      },
    },
  }));

  return (
    <BrowserRouter>
      <Box sx={{ display: 'flex' }}>
        <CssBaseline />
        <AppBar
          position="fixed"
          sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
        >
          <Toolbar>
            <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}>
              Podcase
          </Typography>
            <Search>
              <SearchIconWrapper>
                <SearchIcon />
              </SearchIconWrapper>
              <StyledInputBase
                placeholder="Search…"
                inputProps={{ 'aria-label': 'search' }}
              />
            </Search>
          </Toolbar>

        </AppBar>
        <SideBar></SideBar>
        <Box component="main" sx={{ width: "100%" }}>
          <Box
            sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3, paddingBottom: "150px", minWidth: "100%" }}
          >
            <Toolbar />
            <Routes>
              <Route path="/" element={<PodcastGrid state={GridRoutes.PODCAST_SUBSCRIPTION}></PodcastGrid>} />
              <Route path="all" element={<PodcastGrid state={GridRoutes.PODCAST_ALL}></PodcastGrid>} />
              <Route path="/podcast/:id" element={<PodcastList setCurrentEpisode={setCurrentEpisode}></PodcastList>} />
            </Routes>
          </Box>
          <Playbar currentEpisode={currentEpisode}></Playbar>
        </Box>

      </Box>
    </BrowserRouter>


  );
}

export default App;
