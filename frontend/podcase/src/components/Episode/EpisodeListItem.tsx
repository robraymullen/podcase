import { SubscribedEpisode } from "../../Types";
import { ListItem, ListItemText, ListItemAvatar, Avatar } from '@mui/material';
import Typography from "@mui/material/Typography";
import Box from '@mui/material/Box';

const EpisodeListItem = ({episode}: {episode: SubscribedEpisode}) => {

    return (
        <Box>
            <ListItem alignItems="flex-start">
                <ListItemAvatar>
                    <Avatar alt={episode.title} src={episode.image_url} />
                </ListItemAvatar>
                <ListItemText>
                    <Typography
                        sx={{ display: 'inline' }}
                        component="span"
                        variant="body2"
                        color="text.primary"
                    >
                        {episode.title}
                    </Typography>
                </ListItemText>
            </ListItem>
        </Box>
    )
};

export default EpisodeListItem;