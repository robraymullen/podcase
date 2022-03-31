import { SubscribedEpisode } from "../../Types";
import { ListItem, ListItemText, ListItemAvatar, Grid } from '@mui/material';
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


interface EpisodeListItemInterface {
    children: never[];
    key: string;
    episode: SubscribedEpisode;
    setCurrentEpisode: Function;
    setDialogOpen: Function;
    setDialogDescription: Function;
    imageUrl: string;
}

const EpisodeListItem = ({ episode, setCurrentEpisode, setDialogOpen, setDialogDescription, imageUrl }: EpisodeListItemInterface) => {

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    const pubDate = new Date(episode.publication_date);
    const percentPlayed = ((episode.play_length ? episode.play_length : 0) / episode.duration) * 100;
    const percentageProps: CircularProgressProps & { value: number } = { value: percentPlayed };

    const selectStyle = {
        cursor: "pointer"
    };

    const handleShowDescription = () => {
        setDialogDescription(ReactHtmlParser(episode.description));
        setDialogOpen(true);
    };

    const handlePlay = () => {
        setCurrentEpisode(episode);
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
                                    <Box>
                                        <CalendarMonthIcon></CalendarMonthIcon>
                                        <Typography
                                            variant="caption"
                                        >
                                            {pubDate.toDateString()}
                                        </Typography>
                                    </Box>
                                </Grid>
                                <Grid item>
                                    <Box>
                                        <HeadphonesIcon />
                                        <Typography variant="caption">
                                            {
                                                new Date(episode.duration * 1000).toISOString().substr(11, 8)
                                            }
                                        </Typography>
                                    </Box>
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
                                            <Typography
                                                variant="caption"
                                                component="div"
                                                color="text.secondary"
                                                fontSize="0.5rem"
                                            >{`${Math.round(percentageProps.value)}%`}</Typography>
                                        </Box>
                                    </Box>
                                </Grid>
                                <Grid item>
                                    <IconButton color="primary" onClick={handlePlay}>
                                        <PlayCircleFilledIcon />
                                    </IconButton>

                                </Grid>
                                <Grid item>
                                    <IconButton color="primary" onClick={handleShowDescription}>
                                        <InfoIcon />
                                    </IconButton>

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