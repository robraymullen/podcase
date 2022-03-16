import React from 'react';
import { styled, alpha } from '@mui/material/styles';
import './App.css';
import PodcastGrid from './components/PodcastGrid/PodcastGrid';
import Box from '@mui/material/Box';
import { Divider, Drawer, List, ListItem, ListItemIcon, ListItemText, Toolbar } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Typography from '@mui/material/Typography';
import PodcastsIcon from '@mui/icons-material/Podcasts';
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import AddBoxIcon from '@mui/icons-material/AddBox';
import InputBase from '@mui/material/InputBase';
import MenuIcon from '@mui/icons-material/Menu';
import SearchIcon from '@mui/icons-material/Search';

function App() {

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
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="permanent"
        anchor="left"
      >
        <Toolbar />
        <Divider />
        <List>
          <ListItem button key='Subscriptions'>
            <PodcastsIcon></PodcastsIcon>
            <ListItemText primary='Subscriptions' />
          </ListItem>
          <ListItem button key='Favourites'>
            <BookmarkBorderIcon></BookmarkBorderIcon>
            <ListItemText primary='Favourites' />
          </ListItem>
          <ListItem button key='Add Subscription'>
            <AddBoxIcon></AddBoxIcon>
            <ListItemText primary='Add Subscription' />
          </ListItem>
        </List>
        <Divider />
      </Drawer>
      <Box
        component="main"
        sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3 }}
      >
        <Toolbar />
        <PodcastGrid></PodcastGrid>
      </Box>
    </Box>

  );
}

export default App;
