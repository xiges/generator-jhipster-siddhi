const chalk = require('chalk');
const generator = require('yeoman-generator');
const packagejs = require('../../package.json');

// Stores JHipster variables
const jhipsterVar = { moduleName: 'siddhi' };

// Stores JHipster functions
const jhipsterFunc = {};

module.exports = generator.extend({

    initializing: {
        compose() {
            this.composeWith('jhipster:modules',
                { jhipsterVar, jhipsterFunc },
                this.options.testmode ? { local: require.resolve('generator-jhipster/generators/modules') } : null
            );
        },
        displayLogo() {
            // Have Yeoman greet the user.
            this.log(`Welcome to the ${chalk.bold.yellow('WSO2 Siddhi')} generator! ${chalk.yellow(`v${packagejs.version}\n`)}`);
        }
    },

    prompting() {
        // return the function to call once the task is done
        const done = this.async();

        const prompts = [
            {
                type: 'input',
                name: 'userSiddhi',
                message: 'Please write your own Siddhi app',
                default: 'Do. Or do not. There is no try.'
            }
        ];


        this.prompt(prompts).then((props) => {
            this.props = props;
            // To access props later use this.props.someOption;

            done();
        });
    },

    writing() {
        // function to use directly template
        this.template = function (source, destination) {
            this.fs.copyTpl(
                this.templatePath(source),
                this.destinationPath(destination),
                this
            );
        };

        this.baseName = jhipsterVar.baseName;
        this.packageName = jhipsterVar.packageName;
        this.packageFolder = jhipsterVar.packageFolder;
        this.angularAppName = jhipsterVar.angularAppName;
        this.clientFramework = jhipsterVar.clientFramework;
        this.clientPackageManager = jhipsterVar.clientPackageManager;
        const javaDir = jhipsterVar.javaDir;
        const resourceDir = jhipsterVar.resourceDir;
        const webappDir = jhipsterVar.webappDir;



        this.template('src/main/java/package/domain/_RealtimeAnalyticsServiceImpl.java', `${javaDir}domain/RealtimeAnalyticsServiceImpl.java`);
        this.template('src/main/java/package/domain/_RealtimeAnalyticsService.java', `${javaDir}domain/RealtimeAnalyticsService.java`);
        this.template('src/main/java/package/domain/_TemperatureData.java', `${javaDir}domain/TemperatureData.java`);

        this.template('src/main/java/package/web/rest/_TemperatureDataResource .java', `${javaDir}web/rest/TemperatureDataResource.java`);
    },

    install() {


        this.addMavenDependency('org.wso2.siddhi', 'siddhi-query-api', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-query-compiler', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-annotations', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-core', '4.1.7');
    },

    end() {
        this.log('End of WSO2 Siddhi generator');
    }
});
