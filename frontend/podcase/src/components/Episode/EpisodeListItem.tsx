import { PlayState, SubscribedEpisode } from "../../Types";
import { ListItem, ListItemText, ListItemAvatar, Grid, Tooltip } from '@mui/material';
import Typography from "@mui/material/Typography";
import Box from '@mui/material/Box';
import PlayCircleFilledIcon from '@mui/icons-material/PlayCircleFilled';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import HeadphonesIcon from '@mui/icons-material/Headphones';
import CircularProgress, {
    CircularProgressProps,
} from '@mui/material/CircularProgress';
import InfoIcon from '@mui/icons-material/Info';
import ReactHtmlParser from 'react-html-parser';
import IconButton from '@mui/material/IconButton';
import { AppContext } from "../../context/context";
import { useContext, useState } from 'react';
import { changeEpisode, setAutoPlay } from "../../context/reducer";
import { updateLastPlayed } from "../../services/PodcaseAPIService";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';


interface EpisodeListItemInterface {
    children: never[];
    key: string;
    episode: SubscribedEpisode;
    setDialogOpen: Function;
    setDialogDescription: Function;
    imageUrl: string;
}

const EpisodeListItem = ({ episode, setDialogOpen, setDialogDescription, imageUrl }: EpisodeListItemInterface) => {

    const { state, dispatch } = useContext(AppContext);
    const pubDate = new Date(episode.publication_date);
    const percentPlayed = ((episode.play_length ? episode.play_length : 0) / episode.duration) * 100;

    const [percentageProps, setPercentageProps] = useState<CircularProgressProps & { value: number }>({value: percentPlayed});

    const handleShowDescription = () => {
        setDialogDescription(ReactHtmlParser(episode.description));
        setDialogOpen(true);
    };

    const handlePlay = () => {
        if (state.currentUser) {
            updateLastPlayed(state.currentUser.id, episode, (playState: PlayState) => {
                if (playState.id) {
                    if (episode.play_length >= playState.playLength) {
                        episode.play_length = 0;
                    }
                    episode.play_state_id = playState.id;
                    episode.play_length = playState.playLength ? playState.playLength : 0;
                    dispatch(setAutoPlay(true));
                    dispatch(changeEpisode(episode));
                }
            });
        }
    };

    const setPlayed = () => {
        if (state.currentUser) {
            episode.play_length = episode.duration;
            updateLastPlayed(state.currentUser.id, episode, () => {
                setPercentageProps({ value: 100 });
            });
        }
    };

    return (
        <div>
            <ListItem
                sx={{ border: 1, borderColor: 'primary.main', marginBottom: "1%", width: "100%" }}>

                <Grid container spacing={2}>
                    <Grid item>
                        <Box
                            display="flex"
                            position="relative"
                            alignItems="center"
                            justifyContent="center"
                        >
                            <Box
                                marginTop="20%"
                            >
                                <ListItemAvatar>
                                    <img src={imageUrl} height="60" width="60" />
                                </ListItemAvatar>
                            </Box>

                        </Box>
                    </Grid>
                    <Grid item xs={12} sm container className="listItemNoHover">
                        <Grid item xs container direction="column" spacing={2}>
                            <Grid item xs>
                                <ListItemText  >
                                    <Typography
                                        sx={{ display: 'inline' }}
                                        component="span"
                                        variant="body2"
                                        color="text.primary"
                                    >
                                        {episode.title}
                                    </Typography>
                                </ListItemText>
                            </Grid>
                            <Grid item xs container direction="row" spacing={2}>
                                <Grid item>
                                    <Tooltip title="Publication date" placement="bottom">
                                        <Box>
                                            <CalendarMonthIcon></CalendarMonthIcon>
                                            <Typography
                                                variant="caption"
                                            >
                                                {pubDate.toDateString()}
                                            </Typography>
                                        </Box>
                                    </Tooltip>
                                </Grid>
                                <Grid item>
                                    <Tooltip title="Length of episode" placement="bottom">
                                        <Box>
                                            <HeadphonesIcon />
                                            <Typography variant="caption">
                                                {
                                                    new Date(episode.duration * 1000).toISOString().substr(11, 8)
                                                }
                                            </Typography>
                                        </Box>
                                    </Tooltip>
                                </Grid>
                                <Grid item>
                                    <Box sx={{ position: 'relative', display: 'inline-flex' }} >
                                        <CircularProgress variant="determinate" {...percentageProps} size={25} />
                                        <Box
                                            sx={{
                                                top: 0,
                                                left: 0,
                                                bottom: 0,
                                                right: 0,
                                                position: 'absolute',
                                                display: 'flex',
                                                alignItems: 'center',
                                                justifyContent: 'center',
                                            }}
                                        >
                                            <Tooltip title="% of episode played" placement="bottom">
                                                <Typography
                                                    variant="caption"
                                                    component="div"
                                                    color="text.secondary"
                                                    fontSize="0.5rem"
                                                >{`${Math.round(percentageProps.value)}%`}</Typography>
                                            </Tooltip>
                                        </Box>
                                    </Box>
                                </Grid>
                                <Grid item>
                                    <Tooltip title="Play" placement="bottom">
                                        <IconButton color="primary" onClick={handlePlay}>
                                            <PlayCircleFilledIcon />
                                        </IconButton>
                                    </Tooltip>
                                </Grid>
                                <Grid item>
                                    <Tooltip title="Show description" placement="bottom">
                                        <IconButton color="primary" onClick={handleShowDescription}>
                                            <InfoIcon />
                                        </IconButton>
                                    </Tooltip>
                                </Grid>
                                <Grid item>
                                    <Tooltip title="Mark as played" placement="bottom">
                                        <IconButton color="primary" onClick={setPlayed}>
                                            <CheckCircleIcon />
                                        </IconButton>
                                    </Tooltip>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </ListItem>
        </div>



    )
};

export default EpisodeListItem;