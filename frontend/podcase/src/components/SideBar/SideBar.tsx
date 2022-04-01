import { Divider, Drawer, List, ListItem, ListItemText, Toolbar } from '@mui/material';
import { useNavigate } from "react-router-dom";
import PodcastsIcon from '@mui/icons-material/Podcasts';
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import AddBoxIcon from '@mui/icons-material/AddBox';
import { GridRoutes } from '../../Types';
import Typography from '@mui/material/Typography';

const SideBar = () => {
  const drawerWidth = 240;
  const navigate = useNavigate();

  return (
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
      <Toolbar sx={{
        backgroundColor: "#1976d2",
        color: "white",
      }}>
        <Typography variant="h6">
          PODCASE
          </Typography>
      </Toolbar>


      <Divider />
      <List>
        <ListItem button key='Subscriptions' onClick={() => navigate("/", { state: { podcastState: GridRoutes.PODCAST_SUBSCRIPTION } })}>
          <PodcastsIcon></PodcastsIcon>
          <ListItemText primary='Subscriptions' />
        </ListItem>
        <ListItem button key='All Podcasts' onClick={() => navigate("/all", { state: { podcastState: GridRoutes.PODCAST_ALL } })}>
          <PodcastsIcon></PodcastsIcon>
          <ListItemText primary='All Podcasts' />
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
    </Drawer >
  )
}

export default SideBar;
